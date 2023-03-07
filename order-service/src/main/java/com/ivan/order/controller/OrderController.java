package com.ivan.order.controller;

import com.ivan.order.dto.OrderRequest;
import com.ivan.order.response.ApiResponse;
import com.ivan.order.service.OrderPresentationService;
import com.ivan.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<ApiResponse>> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(new ApiResponse(1, orderService.placeOrder(orderPresentationService.convertToModelList(orderRequest))), HttpStatus.CREATED));
    }

    public CompletableFuture<ResponseEntity<ApiResponse>> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(new ApiResponse(0, "Something went wrong, please try again later"), HttpStatus.GATEWAY_TIMEOUT));
    }
}
