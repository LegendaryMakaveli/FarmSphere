package com.farmSphere.marketplace.controller;

import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.marketplace.dto.request.ListProduceRequest;
import com.farmSphere.marketplace.dto.request.UpdateProduceRequest;
import com.farmSphere.marketplace.service.ProduceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farmers/produce")
@RequiredArgsConstructor
@PreAuthorize("hasRole('FARMER')")
public class FarmerProduceController {

    private final ProduceService produceService;

    @PostMapping("/list-produce")
    public ResponseEntity<ApiResponse<?>> listProduce(@RequestBody ListProduceRequest request) {
        Long farmerId     = SecurityUtils.getCurrentUserId();
        String farmerName = SecurityUtils.getCurrentUserFirstName()
                + " " + SecurityUtils.getCurrentUserLastName();
        String farmerEmail = SecurityUtils.getCurrentUserEmail();

        return ResponseEntity.status(201).body(ApiResponse.success("Produce listed successfully", produceService.listProduce(farmerId, farmerName, farmerEmail, request)));
    }

    @GetMapping("/get-my-listing")
    public ResponseEntity<ApiResponse<?>> getMyProduce() {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your produce listings", produceService.getMyProduce(farmerId)));
    }

    @PatchMapping("/update/produce{produceId}")
    public ResponseEntity<ApiResponse<?>> updateProduce(@PathVariable Long produceId, @RequestBody UpdateProduceRequest request) {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Produce updated", produceService.updateProduce(produceId, farmerId, request)));
    }

    @DeleteMapping("/delete-produce/{produceId}")
    public ResponseEntity<ApiResponse<?>> deleteProduce(@PathVariable Long produceId) {
        Long farmerId = SecurityUtils.getCurrentUserId();
        produceService.deleteProduce(produceId, farmerId);
        return ResponseEntity.ok(ApiResponse.success("Produce removed"));
    }
}
