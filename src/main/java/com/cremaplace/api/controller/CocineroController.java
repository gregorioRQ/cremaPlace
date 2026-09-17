package com.cremaplace.api.controller;

import com.cremaplace.api.service.OrderService;
import com.cremaplace.api.service.CatalogService;
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
        model.addAttribute("pedidos", orderService.getPedidosParaCocina());
        model.addAttribute("alfajores", catalogService.getAllAlfajores());
        model.addAttribute("tartas", catalogService.getAllTartas());
        return "dashboard";
    }

    @PostMapping("/pedidos/iniciar")
    public String iniciarPreparacion(@RequestParam String id, @RequestParam(defaultValue = "0") int tiempoEstimado) {
        orderService.iniciarPreparacion(id, tiempoEstimado);
        return "redirect:/dashboard/cocinero";
    }

    @PostMapping("/pedidos/completar")
    public String completarPedido(@RequestParam String id) {
        orderService.completarPedido(id);
        return "redirect:/dashboard/cocinero";
    }
}
