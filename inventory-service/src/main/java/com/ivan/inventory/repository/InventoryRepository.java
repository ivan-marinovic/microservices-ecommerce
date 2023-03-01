package com.ivan.inventory.repository;

import com.ivan.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductIdIn(List<Integer> productId);
}
