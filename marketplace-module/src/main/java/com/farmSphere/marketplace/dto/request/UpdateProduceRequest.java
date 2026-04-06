package com.farmSphere.marketplace.dto.request;


import com.farmSphere.core.enums.PRODUCE_STATUS;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProduceRequest {
    private Float quantityAvailable;
    private BigDecimal pricePerUnit;
    private String description;
    private String imageUrl;
    private PRODUCE_STATUS status;
}
