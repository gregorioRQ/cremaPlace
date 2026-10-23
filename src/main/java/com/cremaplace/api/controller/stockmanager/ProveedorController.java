package com.cremaplace.api.controller.stockmanager;

import com.cremaplace.api.model.mongo.ProveedorMongo;
import com.cremaplace.api.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Controlador para gestión de proveedores (solo lectura para STOCK_MANAGER)
 */
@Controller
@RequestMapping("/dashboard/stock/proveedores")
@PreAuthorize("hasRole('STOCK_MANAGER')")
public class ProveedorController {
    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("")
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.findAll());
        return "stock-manager/proveedores/lista";
    }
}