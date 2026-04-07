package com.farmSphere.marketplace.service;


import com.farmSphere.core.enums.ORDER_STATUS;
import com.farmSphere.core.enums.PRODUCE_STATUS;
import com.farmSphere.core.event.marketplace.*;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.marketplace.data.model.Order;
import com.farmSphere.marketplace.data.model.OrderItem;
import com.farmSphere.marketplace.data.model.Produce;
import com.farmSphere.marketplace.data.model.SaleConfirmation;
import com.farmSphere.marketplace.data.repository.OrderItemRepository;
import com.farmSphere.marketplace.data.repository.OrderRepository;
import com.farmSphere.marketplace.data.repository.ProduceRepository;
import com.farmSphere.marketplace.data.repository.SaleConfirmationRepository;
import com.farmSphere.marketplace.dto.request.CancelOrderRequest;
import com.farmSphere.marketplace.dto.request.ConfirmSaleRequest;
import com.farmSphere.marketplace.dto.request.OrderItemRequest;
import com.farmSphere.marketplace.dto.request.PlaceOrderRequest;
import com.farmSphere.marketplace.dto.response.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.farmSphere.marketplace.util.Validation.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProduceRepository produceRepository;
    private final SaleConfirmationRepository saleConfirmationRepository;
    private final DomainEventPublisher eventPublisher;


    @Transactional
    @Override
    public OrderResponse placeOrder(Long buyerId, String buyerEmail, String buyerName, String buyerPhone, PlaceOrderRequest request) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Produce> produceList = new ArrayList<>();
        for (OrderItemRequest itemReq : request.getItems()) {
            Produce produce = produceRepository.findById(itemReq.getProduceId()).orElseThrow(() -> new DomainException("Produce not found: " + itemReq.getProduceId(), 404));
            if (produce.getStatus() != PRODUCE_STATUS.AVAILABLE) {throw new DomainException(produce.getCropName() + " is no longer available", 400);}
            if (itemReq.getQuantity() > produce.getQuantityAvailable()) {throw new DomainException("Only " + produce.getQuantityAvailable() + " " + produce.getUnit() + " of " + produce.getCropName() + " available", 400);}

            produceList.add(produce);
            totalAmount = totalAmount.add(produce.getPricePerUnit().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }
        Order order = getOrder(buyerId, buyerEmail, buyerName, buyerPhone, totalAmount);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> savedItems = new ArrayList<>();
        for (int count = 0; count < request.getItems().size(); count++) {

            OrderItemRequest itemReq = request.getItems().get(count);
            Produce produce = produceList.get(count);

            OrderItem item = getOrderItem(savedOrder, produce, itemReq);
            savedItems.add(orderItemRepository.save(item));

            float newQty = produce.getQuantityAvailable() - itemReq.getQuantity();
            produce.setQuantityAvailable(newQty);
            if (newQty == 0) produce.setStatus(PRODUCE_STATUS.SOLD_OUT);
            produceRepository.save(produce);
        }

        savedOrder.setItems(savedItems);

        eventPublisher.publish(new OrderPlacedEvent(
                savedOrder.getOrderId(),
                buyerId,
                buyerEmail,
                buyerName,
                totalAmount,
                request.getItems().size()
        ));

        return OrderResponse.from(savedOrder);
    }


    @Transactional
    @Override
    public OrderResponse cancelOrder(Long orderId, Long buyerId, CancelOrderRequest request) {
        Order order = orderRepository.findByOrderIdAndBuyerId(orderId, buyerId).orElseThrow(() -> new DomainException("Order not found", 404));
        if (order.getStatus() != ORDER_STATUS.PENDING) throw new DomainException("Only pending orders can be cancelled", 400);

        for (OrderItem item : order.getItems()) {
            Produce produce = item.getProduce();
            produce.setQuantityAvailable(produce.getQuantityAvailable() + item.getQuantity());
            produce.setStatus(PRODUCE_STATUS.AVAILABLE);
            produceRepository.save(produce);
        }

        order.setStatus(ORDER_STATUS.CANCELLED);
        order.setCancellationReason(request.getReason());
        order.setUpdatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);

        eventPublisher.publish(new OrderCancelledEvent(
                saved.getOrderId(),
                buyerId,
                saved.getBuyerEmail()
        ));

        return OrderResponse.from(saved);
    }

    @Transactional
    @Override
    public OrderResponse matchOrder(Long orderId, Long adminId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DomainException("Order not found", 404));

        if (order.getStatus() != ORDER_STATUS.PENDING) throw new DomainException("Only pending orders can be matched", 400);

        for (OrderItem item : order.getItems()) {
            item.setMatchedByAdminId(adminId);
            orderItemRepository.save(item);
        }

        order.setStatus(ORDER_STATUS.MATCHED);
        order.setMatchedByAdminId(adminId);
        order.setUpdatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);

        saved.getItems().forEach(item ->
                eventPublisher.publish(new OrderMatchedEvent(
                        saved.getOrderId(),
                        item.getProduce().getFarmerId(),
                        item.getProduce().getFarmerEmail(),
                        item.getProduce().getCropName(),
                        item.getQuantity(),
                        item.getProduce().getUnit()
                ))
        );

        eventPublisher.publish(new OrderMatchedBuyerEvent(
                saved.getOrderId(),
                saved.getBuyerEmail(),
                saved.getBuyerName()
        ));

        return OrderResponse.from(saved);
    }

    @Transactional
    @Override
    public OrderResponse confirmSale(Long orderId, Long adminId, ConfirmSaleRequest request) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DomainException("Order not found", 404));

        if (order.getStatus() != ORDER_STATUS.MATCHED) throw new DomainException("Only matched orders can be confirmed", 400);

        SaleConfirmation confirmation = getSaleConfirmation(adminId, request, order);
        saleConfirmationRepository.save(confirmation);

        order.setStatus(ORDER_STATUS.CONFIRMED);
        order.setUpdatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);

        eventPublisher.publish(new SaleConfirmedEvent(
                saved.getOrderId(),
                saved.getBuyerId(),
                saved.getBuyerEmail(),
                saved.getBuyerName(),
                saved.getTotalAmount()
        ));

        saved.getItems().forEach(item ->
                eventPublisher.publish(new ProduceSoldEvent(
                        item.getProduce().getFarmerId(),
                        item.getProduce().getFarmerEmail(),
                        item.getProduce().getCropName(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
        );

        return OrderResponse.from(saved);    }

    @Override
    public List<OrderResponse> getMyOrders(Long buyerId) {
        return orderRepository.findAllByBuyerId(buyerId)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId, Long buyerId) {
        return orderRepository.findByOrderIdAndBuyerId(orderId, buyerId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new DomainException("Order not found", 404));
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(ORDER_STATUS status) {
        return orderRepository.findAllByStatus(status)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }
}
