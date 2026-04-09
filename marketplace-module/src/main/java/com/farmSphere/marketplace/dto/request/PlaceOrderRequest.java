package com.farmSphere.marketplace.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class PlaceOrderRequest {
    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequest> items;
}
