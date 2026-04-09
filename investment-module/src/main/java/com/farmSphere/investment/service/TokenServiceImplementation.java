package com.farmSphere.investment.service;


import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.core.enums.LISTING_STATUS;
import com.farmSphere.core.enums.TRANSACTION_TYPE;
import com.farmSphere.core.event.investment.AssetFullyFundedEvent;
import com.farmSphere.core.event.investment.InvestmentMadeEvent;
import com.farmSphere.core.event.investment.TokenSoldEvent;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.investment.data.model.FarmAsset;
import com.farmSphere.investment.data.model.InvestmentTransaction;
import com.farmSphere.investment.data.model.Token;
import com.farmSphere.investment.data.model.TokenListing;
import com.farmSphere.investment.data.repository.FarmAssetRepository;
import com.farmSphere.investment.data.repository.InvestmentTransactionRepository;
import com.farmSphere.investment.data.repository.TokenListingRepository;
import com.farmSphere.investment.data.repository.TokenRepository;
import com.farmSphere.investment.dto.request.BuyTokenRequest;
import com.farmSphere.investment.dto.request.ListTokenRequest;
import com.farmSphere.investment.dto.response.TokenListingResponse;
import com.farmSphere.investment.dto.response.TokenResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.investment.util.Validations.*;

@Service
@RequiredArgsConstructor
public class TokenServiceImplementation implements TokenService{
    private final FarmAssetRepository assetRepository;
    private final TokenRepository tokenRepository;
    private final TokenListingRepository listingRepository;
    private final InvestmentTransactionRepository txnRepository;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    @Override
    public TokenResponse buyFromAsset(Long assetId, Long investorId, String investorEmail, String investorName, BuyTokenRequest request) {
        SecurityUtils.requireInvestor();
        FarmAsset asset = assetRepository.findById(assetId).orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getStatus() != ASSET_STATUS.OPEN) throw new DomainException("This asset is no longer accepting investments", 400);
        if (asset.getFundingDeadline().isBefore(LocalDate.now())) throw new DomainException("Funding deadline has passed", 400);
        if (request.getUnits() > asset.getRemainingUnits()) throw new DomainException("Only " + asset.getRemainingUnits() + " units available", 400);

        BigDecimal totalCost = asset.getUnitPrice().multiply(BigDecimal.valueOf(request.getUnits()));
        Token token = tokenRepository.findByFarmAsset_IdAndOwnerId(assetId, investorId).orElse(null);
        token = getTokenOrAddToExisting(investorId, investorEmail, investorName, request, token, asset, totalCost);

        token.setPurchaseDate(LocalDateTime.now());
        Token saved = tokenRepository.save(token);

        asset.setUnitsSold(asset.getUnitsSold() + request.getUnits());
        asset.setUpdatedAt(LocalDateTime.now());

        if (asset.isFullyFunded()) asset.setStatus(ASSET_STATUS.FUNDED);eventPublisher.publish(new AssetFullyFundedEvent(asset.getId(), asset.getCropPlanId(), asset.getCropName()));

        assetRepository.save(asset);

        recordTransaction(investorId, asset.getId(), TRANSACTION_TYPE.PRIMARY_BUY, request.getUnits(), asset.getUnitPrice(), totalCost);

        eventPublisher.publish(new InvestmentMadeEvent(
                saved.getTokenId(), investorId, investorEmail,
                investorName, assetId, asset.getCropName(),
                request.getUnits(), totalCost));

        return TokenResponse.from(saved);
    }

    @Transactional
    @Override
    public TokenListingResponse listTokenForSale(Long tokenId, Long sellerId, String sellerEmail, String sellerName, ListTokenRequest request) {
        SecurityUtils.requireInvestor();
        Token token = tokenRepository.findById(tokenId).orElseThrow(() -> new DomainException("Token not found", 404));
        if (!token.getOwnerId().equals(sellerId)) throw new DomainException("You do not own this token", 403);
        if (token.isRoiDistributed()) throw new DomainException("Cannot sell token after ROI has been distributed", 400);
        if (request.getUnitsToSell() > token.getUnitsOwned()) throw new DomainException("You only own " + token.getUnitsOwned() + " units", 400);

        long activeListing = listingRepository
                .findAllBySellerIdAndStatus(sellerId, LISTING_STATUS.OPEN)
                .stream()
                .filter(listing -> listing.getToken().getTokenId().equals(tokenId))
                .count();

        if (activeListing > 0) throw new DomainException("You already have an active listing for this token", 409);

        TokenListing listing = getTokenListing(sellerId, sellerEmail, sellerName, request, token);

        return TokenListingResponse.from(listingRepository.save(listing));
    }

    @Transactional
    @Override
    public TokenResponse buyFromListing(Long listingId, Long buyerId, String buyerEmail, String buyerName) {
        SecurityUtils.requireInvestor();
        TokenListing listing = listingRepository.findById(listingId).orElseThrow(() -> new DomainException("Listing not found", 404));
        if (listing.getStatus() != LISTING_STATUS.OPEN) throw new DomainException("Listing is no longer available", 400);
        if (listing.getSellerId().equals(buyerId)) throw new DomainException("You cannot buy your own listing", 400);

        Token sellerToken = listing.getToken();
        FarmAsset asset = sellerToken.getFarmAsset();

        BigDecimal totalCost = listing.getAskingPricePerUnit().multiply(BigDecimal.valueOf(listing.getUnitsToSell()));

        int newSellerUnits = sellerToken.getUnitsOwned() - listing.getUnitsToSell();
        if (newSellerUnits == 0) {
            tokenRepository.delete(sellerToken);
        } else {
            BigDecimal newSellerTotal = sellerToken.getPurchasePricePerUnit().multiply(BigDecimal.valueOf(newSellerUnits));
            sellerToken.setUnitsOwned(newSellerUnits);
            sellerToken.setTotalInvested(newSellerTotal);
            tokenRepository.save(sellerToken);
        }

        Token buyerToken = tokenRepository.findByFarmAsset_IdAndOwnerId(asset.getId(), buyerId).orElse(null);
        buyerToken = getBuyerToken(buyerId, buyerEmail, buyerName, buyerToken, asset, listing, totalCost);

        buyerToken.setPurchaseDate(LocalDateTime.now());
        Token savedBuyerToken = tokenRepository.save(buyerToken);
        getTokenRecord(buyerId, buyerEmail, buyerName, listing);

        listingRepository.save(listing);

        recordTransaction(buyerId, asset.getId(),
                TRANSACTION_TYPE.SECONDARY_BUY,
                listing.getUnitsToSell(),
                listing.getAskingPricePerUnit(), totalCost);

        recordTransaction(listing.getSellerId(), asset.getId(),
                TRANSACTION_TYPE.SECONDARY_SELL,
                listing.getUnitsToSell(),
                listing.getAskingPricePerUnit(), totalCost);

        eventPublisher.publish(new TokenSoldEvent(
                listingId, listing.getSellerId(), listing.getSellerEmail(),
                buyerId, buyerEmail, asset.getCropName(),
                listing.getUnitsToSell(), totalCost));

        return TokenResponse.from(savedBuyerToken);
    }


    @Override
    public void cancelListing(Long listingId, Long sellerId) {
        SecurityUtils.requireInvestor();
        TokenListing listing = listingRepository.findById(listingId).orElseThrow(() -> new DomainException("Listing not found", 404));
        if (!listing.getSellerId().equals(sellerId)) throw new DomainException("You do not own this listing", 403);
        if (listing.getStatus() != LISTING_STATUS.OPEN) throw new DomainException("Listing is not open", 400);

        listing.setStatus(LISTING_STATUS.CANCELLED);
        listingRepository.save(listing);
    }

    @Override
    public List<TokenResponse> getMyTokens(Long investorId) {
        SecurityUtils.requireInvestor();
        return tokenRepository.findAllByOwnerId(investorId).stream().map(TokenResponse::from).toList();
    }

    @Override
    public List<TokenListingResponse> getMyListings(Long sellerId) {
        SecurityUtils.requireInvestor();
        return listingRepository.findAllBySellerId(sellerId).stream().map(TokenListingResponse::from).toList();
    }

    @Override
    public List<TokenListingResponse> getOpenListings() {
        SecurityUtils.requireInvestor();
        return listingRepository.findAllByStatus(LISTING_STATUS.OPEN).stream().map(TokenListingResponse::from).toList();
    }

    @Override
    public List<TokenListingResponse> getListingsByAsset(Long assetId) {
        SecurityUtils.requireInvestor();
        return listingRepository.findAllByToken_FarmAsset_Id(assetId)
                .stream()
                .filter(listing -> listing.getStatus() == LISTING_STATUS.OPEN)
                .map(TokenListingResponse::from)
                .toList();
    }


    @Override
    public List<TokenListingResponse> getAllListings() {
        SecurityUtils.requireAdmin();
        return listingRepository.findAll().stream().map(TokenListingResponse::from).toList();
    }
















    private void recordTransaction(Long userId, Long assetId, TRANSACTION_TYPE type, int units, BigDecimal pricePerUnit, BigDecimal totalAmount) {
        InvestmentTransaction txn = new InvestmentTransaction();
        txn.setUserId(userId);
        txn.setAssetId(assetId);
        txn.setType(type);
        txn.setUnits(units);
        txn.setPricePerUnit(pricePerUnit);
        txn.setTotalAmount(totalAmount);
        txn.setTransactionDate(LocalDateTime.now());
        txnRepository.save(txn);
    }
}
