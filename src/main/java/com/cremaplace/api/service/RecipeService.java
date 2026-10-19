package com.cremaplace.api.service;

import com.cremaplace.api.model.RecipeIngredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {
    private final Map<String, List<RecipeIngredient>> recipes = new HashMap<>();
    
    public RecipeService() {
        // Initialize default recipes
        initializeDefaultRecipes();
    }
    
    private void initializeDefaultRecipes() {
        // Default recipe for Alfajor Chocolate Simple (id: a1)
        // Harina: 30g, Azúcar: 15g, Chocolate: 10g, Huevo: 5g
        List<RecipeIngredient> alfajorChocolateSimpleRecipe = new ArrayList<>();
        alfajorChocolateSimpleRecipe.add(new RecipeIngredient("harina", 30));
        alfajorChocolateSimpleRecipe.add(new RecipeIngredient("azucar", 15));
        alfajorChocolateSimpleRecipe.add(new RecipeIngredient("chocolate", 10));
        alfajorChocolateSimpleRecipe.add(new RecipeIngredient("huevo", 5));
        recipes.put("a1", alfajorChocolateSimpleRecipe);
        
        // Default recipe for Alfajor Dulce de Leche Triple (id: a2)
        // Harina: 45g, Azúcar: 20g, Dulce de Leche: 25g, Huevo: 8g
        List<RecipeIngredient> alfajorDulceDeLecheTripleRecipe = new ArrayList<>();
        alfajorDulceDeLecheTripleRecipe.add(new RecipeIngredient("harina", 45));
        alfajorDulceDeLecheTripleRecipe.add(new RecipeIngredient("azucar", 20));
        alfajorDulceDeLecheTripleRecipe.add(new RecipeIngredient("dulce_de_leche", 25));
        alfajorDulceDeLecheTripleRecipe.add(new RecipeIngredient("huevo", 8));
        recipes.put("a2", alfajorDulceDeLecheTripleRecipe);
        
        // Default recipe for Tarta Frutilla Unidad (id: t1)
        // Harina: 200g, Azúcar: 150g, Frutilla: 100g, Huevo: 30g, Mantequilla: 50g
        List<RecipeIngredient> tartaFrutillaUnidadRecipe = new ArrayList<>();
        tartaFrutillaUnidadRecipe.add(new RecipeIngredient("harina", 200));
        tartaFrutillaUnidadRecipe.add(new RecipeIngredient("azucar", 150));
        tartaFrutillaUnidadRecipe.add(new RecipeIngredient("frutilla", 100));
        tartaFrutillaUnidadRecipe.add(new RecipeIngredient("huevo", 30));
        tartaFrutillaUnidadRecipe.add(new RecipeIngredient("mantequilla", 50));
        recipes.put("t1", tartaFrutillaUnidadRecipe);
        
        // Default recipe for Tarta Lemon Pie Unidad (id: t2)
        // Harina: 180g, Azúcar: 120g, Limón: 80g, Huevo: 25g, Mantequilla: 40g
        List<RecipeIngredient> tartaLemonPieUnidadRecipe = new ArrayList<>();
        tartaLemonPieUnidadRecipe.add(new RecipeIngredient("harina", 180));
        tartaLemonPieUnidadRecipe.add(new RecipeIngredient("azucar", 120));
        tartaLemonPieUnidadRecipe.add(new RecipeIngredient("limon", 80));
        tartaLemonPieUnidadRecipe.add(new RecipeIngredient("huevo", 25));
        tartaLemonPieUnidadRecipe.add(new RecipeIngredient("mantequilla", 40));
        recipes.put("t2", tartaLemonPieUnidadRecipe);
    }
    
    public List<RecipeIngredient> getRecipeForProduct(String productId) {
        return recipes.getOrDefault(productId, new ArrayList<>());
    }
    
    public void addRecipe(String productId, List<RecipeIngredient> recipe) {
        recipes.put(productId, recipe);
    }
    
    public void removeRecipe(String productId) {
        recipes.remove(productId);
    }
    
    public boolean hasRecipe(String productId) {
        return recipes.containsKey(productId);
    }
}