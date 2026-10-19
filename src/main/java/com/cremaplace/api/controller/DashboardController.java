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

    @GetMapping("/sin-rol")
    public String sinRol() {
        return "sin-rol";
    }
}