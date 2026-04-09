package com.farmSphere.investment.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class YieldDistributionResponse {
    private Long assetId;
    private String cropName;
    private float actualROI;
    private BigDecimal totalPaidOut;
    private int totalInvestorsPaid;
    private String distributedAt;
}
