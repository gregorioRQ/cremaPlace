package com.cremaplace.api.controller.stockmanager;

import com.cremaplace.api.model.mongo.OrdenCompraMongo;
import com.cremaplace.api.model.mongo.RecepcionMercanciaMongo;
import com.cremaplace.api.service.OrdenCompraService;
import com.cremaplace.api.service.RecepcionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Controlador para registro de recepción de mercancía
 */
@Controller
@RequestMapping("/dashboard/stock/recepciones")
@PreAuthorize("hasRole('STOCK_MANAGER')")
public class RecepcionController {
    private final RecepcionService recepcionService;
    private final OrdenCompraService ordenService;

    public RecepcionController(RecepcionService recepcionService,
                               OrdenCompraService ordenService) {
        this.recepcionService = recepcionService;
        this.ordenService = ordenService;
    }

    @GetMapping("/nuevo/{ordenId}")
    public String mostrarFormularioRecepcion(@PathVariable String ordenId, Model model) {
        return ordenService.obtenerOrden(ordenId)
                .map(orden -> {
                    model.addAttribute("orden", orden);
                    model.addAttribute("recepcion", new RecepcionMercanciaMongo());
                    // Pre-establecer el ID de la orden en la recepción
                    ((RecepcionMercanciaMongo) model.getAttribute("recepcion")).setOrdenCompraId(ordenId);
                    return "stock-manager/recepciones/registro";
                })
                .orElse("redirect:/dashboard/stock/ordenes");
    }

    @PostMapping("")
    public String registrarRecepcion(@ModelAttribute RecepcionMercanciaMongo recepcion) {
        recepcionService.registrarRecepcion(recepcion);
        return "redirect:/dashboard/stock/ordenes";
    }

    @GetMapping("/{id}")
    public String detalleRecepcion(@PathVariable String id, Model model) {
        return recepcionService.obtenerRecepcion(id)
                .map(recepcion -> {
                    model.addAttribute("recepcion", recepcion);
                    return "stock-manager/recepciones/detalle";
                })
                .orElse("redirect:/dashboard/stock/recepciones");
    }

    @GetMapping("")
    public String listarRecepciones(Model model) {
        // Necesitaríamos implementar este método en el servicio
        // Por ahora, dejaremos vacío
        model.addAttribute("recepciones", java.util.Collections.emptyList());
        return "stock-manager/recepciones/lista";
    }
}