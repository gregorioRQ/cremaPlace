package com.cremaplace.api.model;

public class RecipeIngredient {
    private String ingredientId;
    private int quantity; // quantity needed per unit of product
    
    public RecipeIngredient(String ingredientId, int quantity) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
    
    // Constructors
    public RecipeIngredient() {
    }

    // Getters and Setters
    public String getIngredientId() { return ingredientId; }
    public int getQuantity() { return quantity; }
    
    public void setIngredientId(String ingredientId) { this.ingredientId = ingredientId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}