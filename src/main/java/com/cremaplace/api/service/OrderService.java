package com.cremaplace.api.service;

import com.cremaplace.api.model.Alfajor;
import com.cremaplace.api.model.Ingredient;
import com.cremaplace.api.model.OrderItem;
import com.cremaplace.api.model.Pedido;
import com.cremaplace.api.model.RecipeIngredient;
import com.cremaplace.api.model.Tarta;
import com.cremaplace.api.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final CatalogService catalogService;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UserService userService;

    public OrderService(CatalogService catalogService,
                        IngredientService ingredientService,
                        RecipeService recipeService,
                        UserService userService) {
        this.catalogService = catalogService;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    /**
     * Crear un nuevo pedido con información completa para la cocina.
     */
    public void createOrder(String userEmail, List<String> alfajoresIds, List<String> tartasIds) {
        if (alfajoresIds.size() < 2 || tartasIds.size() < 1) {
            throw new RuntimeException("Minimo de compra: 2 Alfajores y 1 Tarta");
        }

        String id = String.valueOf(pedidos.size() + 1);
        LocalDateTime ahora = LocalDateTime.now();

        // Obtener nombre del cliente (parte antes del @ o correo completo)
        String clienteNombre = userEmail.contains("@") ? userEmail.split("@")[0] : userEmail;

        // Construir items y calcular total
        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        // Procesar alfajores (tipo ALFAJOR)
        for (String alfajorId : alfajoresIds) {
            Alfajor a = catalogService.getAlfajorById(alfajorId);
            if (a != null) {
                OrderItem item = new OrderItem(
                    alfajorId,
                    a.getSabor() + " " + a.getTamano(),
                    "ALFAJOR",
                    1,
                    a.getPrecio()
                );
                items.add(item);
                total += a.getPrecio();
            }
        }

        // Procesar tartas (tipo TARTA)
        for (String tartaId : tartasIds) {
            Tarta t = catalogService.getTartaById(tartaId);
            if (t != null) {
                OrderItem item = new OrderItem(
                    tartaId,
                    t.getSabor(),
                    "TARTA",
                    1,
                    t.getPrecioUnidad()
                );
                items.add(item);
                total += t.getPrecioUnidad();
            }
        }

        Pedido pedido = new Pedido(id, userEmail, clienteNombre, total, ahora, items, alfajoresIds, tartasIds);
        // Tiempo estimado inicial basado en suma de tiempos base
        int suggestedTime = calculateSuggestedTime(alfajoresIds, tartasIds);
        pedido.setTiempoEstimadoMinutos(suggestedTime);

        pedidos.add(pedido);
    }

    // ----- METODOS PARA COCINERO -----

    /** Obtener pedidos que la cocina debe ver (Pagado o En Preparación) con datos completos */
    public List<Pedido> getPedidosParaCocinaDetallado() {
        return pedidos.stream()
                .filter(p -> p.getEstado().equals("Pagado") || p.getEstado().equals("En Preparación"))
                .collect(Collectors.toList());
    }

    /** Obtener detalle completo de un pedido por ID */
    public Pedido getOrderDetail(String id) {
        return pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Iniciar preparación: deduce ingredientes inmediatamente, establece estado
     * y guarda tiempo estimado proporcionado.
     */
    public void iniciarPreparacion(String id, int tiempoEstimado) {
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (pedido == null) {
            return;
        }
        // Solo se puede iniciar si está Pagado
        if (!pedido.getEstado().equals("Pagado")) {
            return;
        }
        pedido.setEstado("En Preparación");
        pedido.setTiempoEstimadoMinutos(tiempoEstimado);
        deductIngredientsForOrder(id); // Deduce stock inmediatamente
    }

    /** Terminar preparación: simplemente cambia estado a Listo (ya se dedujo stock) */
    public void terminarPreparacion(String id) {
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (pedido == null) {
            return;
        }
        if (!pedido.getEstado().equals("En Preparación")) {
            return;
        }
        pedido.setEstado("Listo");
    }

    /** Cancelar pedido (SALTEAR): marca como cancelado con motivo, NO restaura stock */
    public void cancelarPedido(String id, String motivo) {
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (pedido == null) {
            return;
        }
        // Solo se puede cancelar si aún no está Listo/Entregado/Cancelado
        if (pedido.getEstado().equals("Listo") || pedido.getEstado().equals("Entregado") || pedido.getEstado().equals("Cancelado")) {
            return;
        }
        pedido.setEstado("Cancelado");
        pedido.setCancelado(true);
        pedido.setCancelReason(motivo == null || motivo.trim().isEmpty() ? "sin motivo" : motivo.trim());
    }

    // ----- MÉTODOS EXISTENTES ADAPTADOS -----

    public List<Pedido> getPedidosByUser(String email) {
        return pedidos.stream().filter(p -> p.getUserEmail().equals(email)).collect(Collectors.toList());
    }

    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    public List<Pedido> getPedidosByEstado(String estado) {
        return pedidos.stream().filter(p -> p.getEstado().equals(estado)).collect(Collectors.toList());
    }

    // Mantener compatibilidad: devuelve pedidos para cocina (Pagado/En Prep) sin enriquecer? Ahora usamos detallado.
    public List<Pedido> getPedidosParaCocina() {
        return getPedidosParaCocinaDetallado();
    }

    public void updateEstado(String id, String estado) {
        pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> p.setEstado(estado));
    }

    // ----- INGREDIENTES & STOCK -----

    private void deductIngredientsForOrder(String orderId) {
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId().equals(orderId))
                .findFirst()
                .orElse(null);
        if (pedido == null) {
            return;
        }
        // Deducir por cada producto (alfajores y tartas)
        for (String alfajorId : pedido.getAlfajoresIds()) {
            deductIngredientsForProduct(alfajorId, 1);
        }
        for (String tartaId : pedido.getTartasIds()) {
            deductIngredientsForProduct(tartaId, 1);
        }
    }

    private void deductIngredientsForProduct(String productId, int quantity) {
        List<RecipeIngredient> recipe = recipeService.getRecipeForProduct(productId);
        for (RecipeIngredient ri : recipe) {
            int totalQty = ri.getQuantity() * quantity;
            ingredientService.useIngredient(ri.getIngredientId(), totalQty);
        }
    }

    /** Calcular tiempo estimado sugerido basado en tiempos base de productos */
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

    /** Obtener ingredientes consolidados necesarios para un pedido (para mostrar en detalle) */
    public Map<Ingredient, Integer> getConsolidatedIngredients(String orderId) {
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId().equals(orderId))
                .findFirst()
                .orElse(null);
        if (pedido == null) {
            return new HashMap<>();
        }
        Map<Ingredient, Integer> consolidated = new HashMap<>();

        // Alfajores
        for (String alfajorId : pedido.getAlfajoresIds()) {
            addRecipeToMap(alfajorId, consolidated);
        }
        // Tartas
        for (String tartaId : pedido.getTartasIds()) {
            addRecipeToMap(tartaId, consolidated);
        }
        return consolidated;
    }

    private void addRecipeToMap(String productId, Map<Ingredient, Integer> map) {
        for (RecipeIngredient ri : recipeService.getRecipeForProduct(productId)) {
            Ingredient ing = ingredientService.getIngredientById(ri.getIngredientId());
            if (ing != null) {
                map.merge(ing, ri.getQuantity(), Integer::sum);
            }
        }
    }

    /** Verificar si hay advertencia de stock bajo para un pedido */
    public boolean hasStockWarning(String orderId) {
        Map<Ingredient, Integer> needed = getConsolidatedIngredients(orderId);
        return needed.entrySet().stream()
                .anyMatch(entry -> entry.getKey().getStock() < entry.getValue());
    }

    /** Obtener lista de ingredientes con stock bajo para un pedido (para UI) */
    public List<Map.Entry<Ingredient, Integer>> getMissingIngredients(String orderId) {
        Map<Ingredient, Integer> needed = getConsolidatedIngredients(orderId);
        return needed.entrySet().stream()
                .filter(entry -> entry.getKey().getStock() < entry.getValue())
                .collect(Collectors.toList());
    }
}