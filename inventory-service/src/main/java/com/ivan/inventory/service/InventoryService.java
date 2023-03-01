package com.ivan.inventory.service;

import com.ivan.inventory.dto.InventoryResponse;
import com.ivan.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<Integer> productId) {
        return inventoryRepository.findByProductIdIn(productId).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .productId(inventory.getProductId())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()).toList();

    }
}
