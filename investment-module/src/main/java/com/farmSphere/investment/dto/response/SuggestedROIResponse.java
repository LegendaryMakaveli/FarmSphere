package com.farmSphere.investment.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class SuggestedROIResponse {
    private Long cropPlanId;
    private Long plotId;
    private String primaryCropName;
    private float totalExpectedYieldKg;
    private BigDecimal marketPricePerUnit;
    private BigDecimal projectedRevenue;
    private String marketUnit;
    private BigDecimal totalInvestment;
    private float suggestedROI;
    private String note;
}
