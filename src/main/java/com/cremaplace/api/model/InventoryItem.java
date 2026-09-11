package com.cremaplace.api.model;

public class InventoryItem {
    private String productId;
    private String productName;
    private int stock;

    public InventoryItem(String productId, String productName, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
