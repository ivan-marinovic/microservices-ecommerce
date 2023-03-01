package com.ivan.order.service;

import com.ivan.order.dto.OrderItemsDto;
import com.ivan.order.dto.OrderRequest;
import com.ivan.order.model.OrderItems;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPresentationService {

    private OrderItems convertToModel(OrderItemsDto orderItemsDto) {
        OrderItems orderLineItems = new OrderItems();
        orderLineItems.setPrice(orderItemsDto.getPrice());
        orderLineItems.setQuantity(orderItemsDto.getQuantity());
        orderLineItems.setProductId(orderItemsDto.getProductId());
        return orderLineItems;
    }

    public List<OrderItems> convertToModelList(OrderRequest orderRequest) {
        return orderRequest.getOrderItemsDtoList()
                .stream()
                .map(this::convertToModel)
                .toList();
    }

}
