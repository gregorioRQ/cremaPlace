package com.cremaplace.api.controller;

import com.cremaplace.api.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class ClienteController {

    private final CatalogService catalogService;

    public ClienteController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/cliente")
    public String clienteDashboard(Model model) {
        model.addAttribute("alfajores", catalogService.getAllAlfajores());
        model.addAttribute("tartas", catalogService.getAllTartas());
        return "cliente";
    }
}