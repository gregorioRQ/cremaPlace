package com.cremaplace.api.controller;

import com.cremaplace.api.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog")
    public String viewCatalog(Model model) {
        model.addAttribute("alfajores", catalogService.getAllAlfajores());
        model.addAttribute("tartas", catalogService.getAllTartas());
        return "catalog";
    }
}
