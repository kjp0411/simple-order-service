package com.kjp0411.simpleorderservice.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderCreateRequest {

    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
