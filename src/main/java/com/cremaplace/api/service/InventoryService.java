package com.cremaplace.api.service;

import com.cremaplace.api.model.InventoryItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final List<InventoryItem> inventory = new ArrayList<>();

    public InventoryService() {
        inventory.add(new InventoryItem("a1", "Alfajor Chocolate Simple", 50));
        inventory.add(new InventoryItem("a2", "Alfajor Dulce de Leche Triple", 30));
        inventory.add(new InventoryItem("t1", "Tarta Frutilla Unidad", 15));
        inventory.add(new InventoryItem("t2", "Tarta Lemon Pie Unidad", 20));
    }

    public List<InventoryItem> getAllInventory() {
        return inventory;
    }

    public void updateStock(String productId, int newStock) {
        inventory.stream()
            .filter(item -> item.getProductId().equals(productId))
            .findFirst()
            .ifPresent(item -> item.setStock(newStock));
    }

    public void addInventoryItem(String productId, String productName, int initialStock) {
        inventory.add(new InventoryItem(productId, productName, initialStock));
    }

    public void removeInventoryItem(String productId) {
        inventory.removeIf(item -> item.getProductId().equals(productId));
    }
}
