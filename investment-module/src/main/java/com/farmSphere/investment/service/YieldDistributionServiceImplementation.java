package com.farmSphere.investment.service;


import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.core.enums.YIELD_STATUS;
import com.farmSphere.core.event.investment.InvestorPaidEvent;
import com.farmSphere.core.event.investment.YieldDistributedEvent;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.investment.data.model.FarmAsset;
import com.farmSphere.investment.data.model.Token;
import com.farmSphere.investment.data.model.YieldDistribution;
import com.farmSphere.investment.data.repository.FarmAssetRepository;
import com.farmSphere.investment.data.repository.TokenRepository;
import com.farmSphere.investment.data.repository.YieldDistributionRepository;
import com.farmSphere.investment.dto.request.RecordHarvestRequest;
import com.farmSphere.investment.dto.response.FarmAssetResponse;
import com.farmSphere.investment.dto.response.YieldDistributionResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.investment.util.Validations.getYieldDistribution;

@Service
@RequiredArgsConstructor
public class YieldDistributionServiceImplementation implements YieldDistributionService{

    private final FarmAssetRepository assetRepository;
    private final TokenRepository tokenRepository;
    private final YieldDistributionRepository yieldRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    public FarmAssetResponse recordHarvest(Long assetId, RecordHarvestRequest request, Long adminId) {
        SecurityUtils.requireAdmin();
        FarmAsset asset = assetRepository.findById(assetId).orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getStatus() == ASSET_STATUS.OPEN) throw new DomainException("Cannot record harvest for an OPEN asset — close funding first", 400);
        if (asset.getStatus() == ASSET_STATUS.PAID_OUT) throw new DomainException("ROI already distributed for this asset", 400);

        asset.setActualROI(request.getActualROI());
        asset.setUpdatedAt(LocalDateTime.now());

        return FarmAssetResponse.from(assetRepository.save(asset));
    }

    @Transactional
    @Override
    public YieldDistributionResponse distributeROI(Long assetId, Long adminId) {
        SecurityUtils.requireAdmin();
        FarmAsset asset = assetRepository.findById(assetId).orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getActualROI() == 0 && asset.getStatus() != ASSET_STATUS.PAID_OUT) throw new DomainException("Record harvest result before distributing ROI", 400);
        if (yieldRepository.existsByFarmAsset_Id(assetId)) throw new DomainException("ROI already distributed for this asset", 409);
        List<Token> tokens = tokenRepository.findAllByFarmAsset_IdAndRoiDistributedFalse(assetId);
        if (tokens.isEmpty()) throw new DomainException("No investors found for this asset", 404);

        BigDecimal totalPaidOut = BigDecimal.ZERO;
        int investorsPaid = 0;

        for (Token token : tokens) {
            BigDecimal returnOnInvestment = token.getTotalInvested()
                    .multiply(BigDecimal.valueOf(asset.getActualROI()))
                    .divide(BigDecimal.valueOf(100), 2,
                            java.math.RoundingMode.HALF_UP);

            BigDecimal payout = token.getTotalInvested().add(returnOnInvestment);
            totalPaidOut = totalPaidOut.add(payout);

            token.setRoiDistributed(true);
            tokenRepository.save(token);
            investorsPaid++;

            eventPublisher.publish(new InvestorPaidEvent(
                    token.getOwnerId(),
                    token.getOwnerEmail(),
                    token.getOwnerName(),
                    asset.getCropName(),
                    token.getUnitsOwned(),
                    token.getTotalInvested(),
                    returnOnInvestment,
                    payout,
                    asset.getActualROI()
            ));
        }

        YieldDistribution distribution = getYieldDistribution(adminId, asset, totalPaidOut, investorsPaid);
        yieldRepository.save(distribution);

        asset.setStatus(ASSET_STATUS.PAID_OUT);
        asset.setUpdatedAt(LocalDateTime.now());
        assetRepository.save(asset);

        eventPublisher.publish(new YieldDistributedEvent(
                assetId, asset.getCropName(), totalPaidOut,
                investorsPaid, asset.getActualROI()));

        return YieldDistributionResponse.builder()
                .assetId(assetId)
                .cropName(asset.getCropName())
                .actualROI(asset.getActualROI())
                .totalPaidOut(totalPaidOut)
                .totalInvestorsPaid(investorsPaid)
                .distributedAt(String.valueOf(LocalDateTime.now()))
                .build();
    }

    @Override
    public YieldDistributionResponse getDistribution(Long assetId) {
        SecurityUtils.requireAdmin();
        YieldDistribution distribution = yieldRepository.findByFarmAsset_Id(assetId).orElseThrow(() -> new DomainException("No distribution found for this asset", 404));

        return YieldDistributionResponse.builder()
                .assetId(assetId)
                .cropName(distribution.getFarmAsset().getCropName())
                .actualROI(distribution.getActualROI())
                .totalPaidOut(distribution.getTotalPaidOut())
                .totalInvestorsPaid(distribution.getTotalInvestorsPaid())
                .distributedAt(distribution.getDistributedAt().toString())
                .build();
    }
}
