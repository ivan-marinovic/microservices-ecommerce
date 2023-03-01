package com.ivan.inventory;

import com.ivan.inventory.model.Inventory;
import com.ivan.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory1 = new Inventory();
            inventory1.setProductId(1);
            inventory1.setQuantity(100);

            Inventory inventory2 = new Inventory();
            inventory2.setProductId(2);
            inventory2.setQuantity(0);

            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory2);
        };
    }
}
