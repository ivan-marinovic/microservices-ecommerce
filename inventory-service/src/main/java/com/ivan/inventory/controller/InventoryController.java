package com.ivan.inventory.controller;

import com.ivan.inventory.dto.InventoryResponse;
import com.ivan.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<Integer> productId) {
        return inventoryService.isInStock(productId);
    }
}
