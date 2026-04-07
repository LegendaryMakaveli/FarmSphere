package com.farmSphere.marketplace.util;

import com.farmSphere.core.enums.ORDER_STATUS;
import com.farmSphere.core.enums.PRODUCE_STATUS;
import com.farmSphere.marketplace.data.model.Order;
import com.farmSphere.marketplace.data.model.OrderItem;
import com.farmSphere.marketplace.data.model.Produce;
import com.farmSphere.marketplace.data.model.SaleConfirmation;
import com.farmSphere.marketplace.dto.request.ConfirmSaleRequest;
import com.farmSphere.marketplace.dto.request.ListProduceRequest;
import com.farmSphere.marketplace.dto.request.OrderItemRequest;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Validation {

    @NonNull
    public static Produce mapToListProduce(Long farmerId, String farmerName, String farmerEmail, ListProduceRequest request) {
        Produce produce = new Produce();
        produce.setFarmerId(farmerId);
        produce.setFarmerName(farmerName);
        produce.setFarmerEmail(farmerEmail);
        produce.setCropName(request.getCropName().trim());
        produce.setCategory(request.getCategory());
        produce.setQuantityAvailable(request.getQuantityAvailable());
        produce.setUnit(request.getUnit().toUpperCase().trim());
        produce.setPricePerUnit(request.getPricePerUnit());
        produce.setHarvestDate(request.getHarvestDate());
        produce.setExpiryDate(request.getExpiryDate());
        produce.setDescription(request.getDescription());
        produce.setImageUrl(request.getImageUrl());
        produce.setStatus(PRODUCE_STATUS.AVAILABLE);
        produce.setListedAt(LocalDateTime.now());
        return produce;
    }

    @NonNull
    public static Order getOrder(Long buyerId, String buyerEmail, String buyerName, String buyerPhone, BigDecimal totalAmount) {
        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setBuyerEmail(buyerEmail);
        order.setBuyerName(buyerName);
        order.setBuyerPhone(buyerPhone);
        order.setTotalAmount(totalAmount);
        order.setStatus(ORDER_STATUS.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    @NonNull
    public static OrderItem getOrderItem(Order savedOrder, Produce produce, OrderItemRequest itemReq) {
        OrderItem item = new OrderItem();
        item.setOrder(savedOrder);
        item.setProduce(produce);
        item.setQuantity(itemReq.getQuantity());
        item.setUnitPriceAtOrder(produce.getPricePerUnit());
        item.setSubtotal(produce.getPricePerUnit().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        return item;
    }

    @NonNull
    public static SaleConfirmation getSaleConfirmation(Long adminId, ConfirmSaleRequest request, Order order) {
        SaleConfirmation confirmation = new SaleConfirmation();
        confirmation.setOrder(order);
        confirmation.setConfirmedByAdminId(adminId);
        confirmation.setConfirmationDate(LocalDateTime.now());
        confirmation.setNotes(request.getNote());
        return confirmation;
    }
}
