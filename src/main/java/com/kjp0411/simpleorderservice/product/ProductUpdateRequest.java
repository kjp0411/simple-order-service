package com.kjp0411.simpleorderservice.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductUpdateRequest {

    @NotBlank
    private String name;

    @Min(0)
    private int price;

    @Min(0)
    private int stock;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
