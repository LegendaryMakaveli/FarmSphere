package com.farmSphere.farming.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecordHarvestRequest {
    @NotEmpty(message = "At least one item yield is required")
    private List<ItemYield> itemYields;



    @Data
    public static class ItemYield {

        @NotNull(message = "Item ID is required")
        private Long itemId;

        @NotNull(message = "Actual yield is required")
        private float actualYield;
    }
}
