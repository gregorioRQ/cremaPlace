package com.cremaplace.api.service;

import com.cremaplace.api.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final List<Ingredient> ingredients = new ArrayList<>();

    public IngredientService() {
        // Initialize with some basic ingredients
        ingredients.add(new Ingredient("harina", "Harina", 1000, 100)); // 1kg initially, alert at 100g
        ingredients.add(new Ingredient("azucar", "Azúcar", 800, 80));   // 800g initially, alert at 80g
        ingredients.add(new Ingredient("chocolate", "Chocolate", 500, 50)); // 500g initially, alert at 50g
        ingredients.add(new Ingredient("huevo", "Huevo", 100, 20));    // 100 units initially, alert at 20
        ingredients.add(new Ingredient("dulce_de_leche", "Dulce de Leche", 600, 60)); // 600g initially, alert at 60g
        ingredients.add(new Ingredient("frutilla", "Frutilla", 400, 40)); // 400g initially, alert at 40g
        ingredients.add(new Ingredient("limon", "Limón", 300, 30));   // 300g initially, alert at 30g
        ingredients.add(new Ingredient("mantequilla", "Mantequilla", 200, 20)); // 200g initially, alert at 20g
    }

    public List<Ingredient> getAllIngredients() {
        return ingredients;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getIngredientId().equals(ingredientId))
                .findFirst()
                .orElse(null);
    }

    public void updateStock(String ingredientId, int newStock) {
        ingredients.stream()
                .filter(ingredient -> ingredient.getIngredientId().equals(ingredientId))
                .findFirst()
                .ifPresent(ingredient -> ingredient.setStock(newStock));
    }

    public void addIngredient(String ingredientId, String ingredientName, int initialStock, int minStockThreshold) {
        ingredients.add(new Ingredient(ingredientId, ingredientName, initialStock, minStockThreshold));
    }

    public void removeIngredient(String ingredientId) {
        ingredients.removeIf(ingredient -> ingredient.getIngredientId().equals(ingredientId));
    }

    public boolean useIngredient(String ingredientId, int quantity) {
        Ingredient ingredient = getIngredientById(ingredientId);
        if (ingredient != null) {
            return ingredient.useStock(quantity);
        }
        return false;
    }

    public void addStock(String ingredientId, int quantity) {
        Ingredient ingredient = getIngredientById(ingredientId);
        if (ingredient != null) {
            ingredient.addStock(quantity);
        }
    }

    public List<Ingredient> getLowStockIngredients() {
        return ingredients.stream()
                .filter(Ingredient::isLowStock)
                .collect(Collectors.toList());
    }

    public int getLowStockCount() {
        return getLowStockIngredients().size();
    }
}