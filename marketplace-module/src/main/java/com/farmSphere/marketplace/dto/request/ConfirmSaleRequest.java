package com.farmSphere.marketplace.dto.request;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfirmSaleRequest {
    @Size(min = 5, max = 100, message = "note must be between 5 and 100 characters")
    private String note;
}
