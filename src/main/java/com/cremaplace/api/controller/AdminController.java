package com.cremaplace.api.controller;

import com.cremaplace.api.service.CatalogService;
import com.cremaplace.api.service.InventoryService;
import com.cremaplace.api.service.OrderService;
import com.cremaplace.api.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final OrderService orderService;

    public AdminController(UserService userService, CatalogService catalogService,
                           InventoryService inventoryService, OrderService orderService) {
        this.userService = userService;
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable String id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/users/{id}/unlock")
    public String unlockUserAccount(@PathVariable String id) {
        userService.unlockUserAccount(id);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/catalog/alfajor/add")
    public String addAlfajor(@RequestParam String sabor, @RequestParam String tamano,
                             @RequestParam double precio, @RequestParam int tiempoPreparacion) {
        catalogService.addAlfajor(sabor, tamano, precio, tiempoPreparacion);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/catalog/alfajor/delete")
    public String deleteAlfajor(@RequestParam String id) {
        catalogService.deleteAlfajor(id);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/catalog/tarta/add")
    public String addTarta(@RequestParam String sabor, @RequestParam double precioUnidad,
                           @RequestParam int tiempoPreparacion) {
        catalogService.addTarta(sabor, precioUnidad, tiempoPreparacion);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/catalog/tarta/delete")
    public String deleteTarta(@RequestParam String id) {
        catalogService.deleteTarta(id);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/inventory/update")
    public String updateStock(@RequestParam String productId, @RequestParam int stock) {
        inventoryService.updateStock(productId, stock);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/pedidos/updateEstado")
    public String updateOrderEstado(@RequestParam String id, @RequestParam String estado) {
        orderService.updateEstado(id, estado);
        return "redirect:/dashboard/admin";
    }
}
