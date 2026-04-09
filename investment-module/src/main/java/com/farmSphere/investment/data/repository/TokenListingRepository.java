package com.farmSphere.investment.data.repository;


import com.farmSphere.core.enums.LISTING_STATUS;
import com.farmSphere.investment.data.model.TokenListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenListingRepository extends JpaRepository<TokenListing, Long> {
    List<TokenListing> findAllByStatus(LISTING_STATUS status);
    List<TokenListing> findAllByToken_FarmAsset_Id(Long assetId);
    List<TokenListing> findAllBySellerId(Long sellerId);
    List<TokenListing> findAllBySellerIdAndStatus(Long sellerId, LISTING_STATUS status);
}
