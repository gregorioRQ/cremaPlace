package com.cremaplace.api.controller;

import com.cremaplace.api.service.InventoryService;
import com.cremaplace.api.service.IngredientService;
import com.cremaplace.api.service.RecipeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/stock")
@PreAuthorize("hasRole('STOCK_MANAGER')")
public class StockManagerController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final InventoryService inventoryService;

    public StockManagerController(IngredientService ingredientService, RecipeService recipeService, InventoryService inventoryService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.inventoryService = inventoryService;
    }

    // Main stock manager dashboard
    @GetMapping("")
    public String stockDashboard(Model model) {
        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        model.addAttribute("lowStockCount", ingredientService.getLowStockCount());
        return "stock-manager/dashboard";
    }

    // Show low stock ingredients
    @GetMapping("/low-stock")
    public String lowStockIngredients(Model model) {
        model.addAttribute("ingredients", ingredientService.getLowStockIngredients());
        return "stock-manager/low-stock";
    }

    // Add stock to an ingredient
    @PostMapping("/add-stock")
    public String addStock(@RequestParam String ingredientId, @RequestParam int quantity) {
        ingredientService.addStock(ingredientId, quantity);
        return "redirect:/dashboard/stock";
    }

    // Get recipe for a product (for admin use)
    @GetMapping("/recipe/{productId}")
    public String getRecipe(@PathVariable String productId, Model model) {
        model.addAttribute("productId", productId);
        model.addAttribute("recipe", recipeService.getRecipeForProduct(productId));
        return "stock-manager/recipe";
    }

    // Finished products inventory management
    @GetMapping("/inventory")
    public String inventory(Model model) {
        model.addAttribute("inventory", inventoryService.getAllInventory());
        return "stock-manager/inventory";
    }

    @PostMapping("/inventory/update")
    public String updateInventoryStock(@RequestParam String productId, @RequestParam int stock) {
        inventoryService.updateStock(productId, stock);
        return "redirect:/dashboard/stock/inventory";
    }
}