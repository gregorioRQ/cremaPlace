package com.cremaplace.api.controller;

import com.cremaplace.api.model.Pedido;
import com.cremaplace.api.service.OrderService;
import com.cremaplace.api.service.CatalogService;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/cocinero")
@PreAuthorize("hasRole('COCINERO')")
public class CocineroController {

    private final OrderService orderService;
    private final CatalogService catalogService;

    public CocineroController(OrderService orderService, CatalogService catalogService) {
        this.orderService = orderService;
        this.catalogService = catalogService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("role", "COCINERO");
        List<Pedido> pedidos = orderService.getPedidosParaCocina();
        // Calculate stock warnings for each pedido
        for (Pedido pedido : pedidos) {
            pedido.setStockWarning(orderService.hasStockWarning(pedido.getId()));
        }
        model.addAttribute("pedidos", pedidos);
        return "cocinero";
    }

    @GetMapping("/pedidos/{id}/detalle")
    public String detallePedido(@PathVariable String id, Model model) {
        Pedido pedido = orderService.getOrderDetail(id);
        if (pedido == null) {
            return "redirect:/dashboard/cocinero";
        }
        model.addAttribute("pedido", pedido);
        // Ingredientes consolidados para la vista de detalle
        model.addAttribute("ingredientesNecesarios", orderService.getConsolidatedIngredients(id));
        return "pedido-detalle";
    }

    @PostMapping("/pedidos/{id}/iniciar")
    public String iniciarPreparacion(@PathVariable String id, @RequestParam int tiempoEstimado) {
        orderService.iniciarPreparacion(id, tiempoEstimado);
        return "redirect:/dashboard/cocinero";
    }

    @PostMapping("/pedidos/{id}/terminar")
    public String terminarPreparacion(@PathVariable String id) {
        orderService.terminarPreparacion(id);
        return "redirect:/dashboard/cocinero";
    }

    @PostMapping("/pedidos/{id}/cancelar")
    public String cancelarPedido(@PathVariable String id, @RequestParam String motivo) {
        orderService.cancelarPedido(id, motivo);
        return "redirect:/dashboard/cocinero";
    }
}
