package com.ivan.order.service;

import com.ivan.order.dto.InventoryResponse;
import com.ivan.order.exception.OutOfStockException;
import com.ivan.order.model.Order;
import com.ivan.order.model.OrderItems;
import com.ivan.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public String placeOrder(List<OrderItems> orderItems) {
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOrderItemsList(orderItems);

        List<Integer> productIds = newOrder.getOrderItemsList().stream()
                .map(OrderItems::getProductId)
                .toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("productId", productIds).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock) {
            orderRepository.save(newOrder);
            return "order placed successfully";
        } else {
            throw new OutOfStockException("out of stock");
        }

    }

}
