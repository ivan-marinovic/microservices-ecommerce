package com.ivan.product.dto;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Builder
public class ProductRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @PositiveOrZero
    private Float price;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
