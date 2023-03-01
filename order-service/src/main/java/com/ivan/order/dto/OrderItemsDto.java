package com.ivan.order.dto;

public class OrderItemsDto {
    private Long id;
    private Integer productId;
    private Float price;
    private Integer quantity;

    public OrderItemsDto() {
    }

    public OrderItemsDto(Long id, Integer productId, Float price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

