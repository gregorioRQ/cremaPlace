package com.cremaplace.api.model;

public class Ingredient {
    private String ingredientId;
    private String ingredientName;
    private int stock; // in grams or units as appropriate
    private int minStockThreshold; // minimum stock level before notification
    
    public Ingredient(String ingredientId, String ingredientName, int stock, int minStockThreshold) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.stock = stock;
        this.minStockThreshold = minStockThreshold;
    }
    
    // Constructors
    public Ingredient() {
        this.minStockThreshold = 10; // default threshold
    }
    
    public Ingredient(String ingredientId, String ingredientName, int stock) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.stock = stock;
        this.minStockThreshold = 10; // default threshold
    }

    // Getters and Setters
    public String getIngredientId() { return ingredientId; }
    public String getIngredientName() { return ingredientName; }
    public int getStock() { return stock; }
    public int getMinStockThreshold() { return minStockThreshold; }
    
    public void setIngredientId(String ingredientId) { this.ingredientId = ingredientId; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }
    public void setStock(int stock) { this.stock = stock; }
    public void setMinStockThreshold(int minStockThreshold) { this.minStockThreshold = minStockThreshold; }
    
    // Helper methods
    public boolean isLowStock() {
        return stock <= minStockThreshold;
    }
    
    public void addStock(int amount) {
        if (amount > 0) {
            this.stock += amount;
        }
    }
    
    public boolean useStock(int amount) {
        if (amount > 0 && this.stock >= amount) {
            this.stock -= amount;
            return true;
        }
        return false;
    }
}