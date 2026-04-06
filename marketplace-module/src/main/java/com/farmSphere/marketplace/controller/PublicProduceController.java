package com.farmSphere.marketplace.controller;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.marketplace.service.ProduceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/produce")
@RequiredArgsConstructor
public class PublicProduceController {

    private final ProduceService produceService;

    @GetMapping("/available-produce")
    public ResponseEntity<ApiResponse<?>> getAllAvailableProduce() {
        return ResponseEntity.ok(ApiResponse.success("Available produce", produceService.getAllAvailableProduce()));
    }

    @GetMapping("/get-produce/{produceId}")
    public ResponseEntity<ApiResponse<?>> getProduceById(@PathVariable Long produceId) {
        return ResponseEntity.ok(ApiResponse.success("Produce detail", produceService.getProduceById(produceId)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<?>> getByCategory(@PathVariable PRODUCE_CATEGORY category) {
        return ResponseEntity.ok(ApiResponse.success("Produce by category", produceService.getByCategory(category)));
    }
}
