package com.cremaplace.api.service;

import com.cremaplace.api.model.Alfajor;
import com.cremaplace.api.model.Ingredient;
import com.cremaplace.api.model.Pedido;
import com.cremaplace.api.model.RecipeIngredient;
import com.cremaplace.api.model.Tarta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final CatalogService catalogService;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public OrderService(CatalogService catalogService, 
                        IngredientService ingredientService,
                        RecipeService recipeService) {
        this.catalogService = catalogService;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    public void createOrder(String userEmail, List<String> alfajoresIds, List<String> tartasIds) {
        if (alfajoresIds.size() < 2 || tartasIds.size() < 1) {
            throw new RuntimeException("Minimo de compra: 2 Alfajores y 1 Tarta");
        }
        Pedido pedido = new Pedido(String.valueOf(pedidos.size() + 1), userEmail, alfajoresIds, tartasIds);
        pedido.setTiempoEstimadoMinutos(calculateSuggestedTime(alfajoresIds, tartasIds));
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidosByUser(String email) {
        return pedidos.stream().filter(p -> p.getUserEmail().equals(email)).collect(Collectors.toList());
    }

    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    public List<Pedido> getPedidosByEstado(String estado) {
        return pedidos.stream().filter(p -> p.getEstado().equals(estado)).collect(Collectors.toList());
    }

    public List<Pedido> getPedidosParaCocina() {
        return pedidos.stream()
            .filter(p -> p.getEstado().equals("Pagado") || p.getEstado().equals("En Preparación"))
            .collect(Collectors.toList());
    }

    public void updateEstado(String id, String estado) {
        pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> p.setEstado(estado));
    }

    public void iniciarPreparacion(String id, int tiempoEstimadoManual) {
        pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> {
            p.setEstado("En Preparación");
            if (tiempoEstimadoManual > 0) {
                p.setTiempoEstimadoMinutos(tiempoEstimadoManual);
            }
        });
    }

    public void completarPedido(String id) {
        updateEstado(id, "Listo");
        // Deduct ingredients when order is completed
        deductIngredientsForOrder(id);
    }
    
    private void deductIngredientsForOrder(String orderId) {
        // Find the order
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId().equals(orderId))
                .findFirst()
                .orElse(null);
        
        if (pedido == null) {
            return;
        }
        
        // Deduct ingredients for alfajores
        for (String alfajorId : pedido.getAlfajoresIds()) {
            deductIngredientsForProduct(alfajorId, 1); // 1 unit of each alfajor
        }
        
        // Deduct ingredients for tartas
        for (String tartaId : pedido.getTartasIds()) {
            deductIngredientsForProduct(tartaId, 1); // 1 unit of each tarta
        }
    }
    
    private void deductIngredientsForProduct(String productId, int quantity) {
        List<RecipeIngredient> recipe = recipeService.getRecipeForProduct(productId);
        for (RecipeIngredient recipeIngredient : recipe) {
            int totalQuantityNeeded = recipeIngredient.getQuantity() * quantity;
            ingredientService.useIngredient(recipeIngredient.getIngredientId(), totalQuantityNeeded);
        }
    }

    private int calculateSuggestedTime(List<String> alfajoresIds, List<String> tartasIds) {
        int alfajoresTime = alfajoresIds.stream()
            .mapToInt(id -> {
                for (Alfajor a : catalogService.getAllAlfajores()) {
                    if (a.getId().equals(id)) {
                        return a.getTiempoPreparacion();
                    }
                }
                return 0;
            })
            .sum();
        
        int tartasTime = tartasIds.stream()
            .mapToInt(id -> {
                for (Tarta t : catalogService.getAllTartas()) {
                    if (t.getId().equals(id)) {
                        return t.getTiempoPreparacion();
                    }
                }
                return 0;
            })
            .sum();
        
        return alfajoresTime + tartasTime;
    }
}
