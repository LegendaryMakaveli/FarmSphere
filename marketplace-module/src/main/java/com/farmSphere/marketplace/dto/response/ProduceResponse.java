package com.farmSphere.marketplace.dto.response;


import com.farmSphere.marketplace.data.model.Produce;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ProduceResponse {

    private Long produceId;
    private Long farmerId;
    private String farmerName;
    private String cropName;
    private String category;
    private float quantityAvailable;
    private String unit;
    private BigDecimal pricePerUnit;
    private String harvestDate;
    private String expiryDate;
    private String description;
    private String imageUrl;
    private String status;
    private String listedAt;
    private String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ProduceResponse from(Produce produce) {
        return ProduceResponse.builder()
                .produceId(produce.getProduceId())
                .farmerId(produce.getFarmerId())
                .farmerName(produce.getFarmerName())
                .cropName(produce.getCropName())
                .category(produce.getCategory().name())
                .quantityAvailable(produce.getQuantityAvailable())
                .unit(produce.getUnit())
                .pricePerUnit(produce.getPricePerUnit())
                .harvestDate(DATE_FORMATTER.format(produce.getHarvestDate()))
                .expiryDate(DATE_FORMATTER.format(produce.getExpiryDate()))
                .description(produce.getDescription())
                .imageUrl(produce.getImageUrl())
                .status(produce.getStatus().name())
                .listedAt(FORMATTER.format(produce.getListedAt()))
                .updatedAt(produce.getUpdatedAt() != null ? FORMATTER.format(produce.getUpdatedAt()) : null)
                .build();
    }
}
