package com.ivan.order.controller;

import com.ivan.order.dto.OrderRequest;
import com.ivan.order.response.ApiResponse;
import com.ivan.order.service.OrderPresentationService;
import com.ivan.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderPresentationService orderPresentationService;

    public OrderController(OrderService orderService, OrderPresentationService orderPresentationService) {
        this.orderService = orderService;
        this.orderPresentationService = orderPresentationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderPresentationService.convertToModelList(orderRequest));
        return new ResponseEntity<>(new ApiResponse(1,"order successfully placed"), HttpStatus.CREATED);
    }
}
