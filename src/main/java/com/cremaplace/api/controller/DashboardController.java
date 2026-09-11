package com.cremaplace.api.controller;

import com.cremaplace.api.service.CatalogService;
import com.cremaplace.api.service.InventoryService;
import com.cremaplace.api.service.OrderService;
import com.cremaplace.api.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final OrderService orderService;

    public DashboardController(UserService userService, CatalogService catalogService,
                               InventoryService inventoryService, OrderService orderService) {
        this.userService = userService;
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("alfajores", catalogService.getAllAlfajores());
        model.addAttribute("tartas", catalogService.getAllTartas());
        model.addAttribute("inventory", inventoryService.getAllInventory());
        model.addAttribute("pedidos", orderService.getAllPedidos());
        return "dashboard";
    }

    @GetMapping("/stock")
    public String stockDashboard(Model model) {
        model.addAttribute("role", "STOCK_MANAGER");
        return "dashboard";
    }

    @GetMapping("/cocinero")
    public String cocineroDashboard(Model model) {
        model.addAttribute("role", "COCINERO");
        return "dashboard";
    }

    @GetMapping("/user")
    public String userDashboard(Model model) {
        model.addAttribute("role", "USER");
        return "dashboard";
    }
}

