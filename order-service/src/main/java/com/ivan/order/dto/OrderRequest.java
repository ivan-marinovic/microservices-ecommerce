package com.ivan.order.dto;

import java.util.List;

public class OrderRequest {
    private List<OrderItemsDto> orderItemsDtoList;

    public OrderRequest() {
    }

    public OrderRequest(List<OrderItemsDto> orderItemsDtoList) {
        this.orderItemsDtoList = orderItemsDtoList;
    }

    public List<OrderItemsDto> getOrderItemsDtoList() {
        return orderItemsDtoList;
    }

    public void setOrderItemsDtoList(List<OrderItemsDto> orderItemsDtoList) {
        this.orderItemsDtoList = orderItemsDtoList;
    }
}
