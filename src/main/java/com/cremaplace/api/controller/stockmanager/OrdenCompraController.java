package com.cremaplace.api.controller.stockmanager;

import com.cremaplace.api.model.mongo.OrdenCompraMongo;
import com.cremaplace.api.model.mongo.OrdenCompraMongo.ItemOrdenMongo;
import com.cremaplace.api.model.mongo.ProveedorMongo;
import com.cremaplace.api.service.IngredientService;
import com.cremaplace.api.service.OrdenCompraService;
import com.cremaplace.api.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestión de órdenes de compra
 */
@Controller
@RequestMapping("/dashboard/stock/ordenes")
@PreAuthorize("hasRole('STOCK_MANAGER')")
public class OrdenCompraController {
    private final OrdenCompraService ordenService;
    private final ProveedorService proveedorService;
    private final IngredientService ingredientService;

    public OrdenCompraController(OrdenCompraService ordenService,
                                 ProveedorService proveedorService,
                                 IngredientService ingredientService) {
        this.ordenService = ordenService;
        this.proveedorService = proveedorService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaOrden(Model model) {
        model.addAttribute("orden", new OrdenCompraMongo());
        model.addAttribute("proveedores", proveedorService.findAll());
        // Proveer lista de IDs de ingredientes para el autocompletado en el formulario
        List<String> ingredientesIds = ingredientService.getAllIngredients().stream()
                .map(ing -> ing.getIngredientId())
                .collect(Collectors.toList());
        model.addAttribute("ingredientesDisponibles", ingredientesIds);
        return "stock-manager/ordenes/crear";
    }

    @PostMapping("")
    public String crearOrden(@ModelAttribute OrdenCompraMongo orden) {
        ordenService.crearOrden(orden);
        return "redirect:/dashboard/stock/ordenes";
    }

    @GetMapping("/{id}")
    public String detalleOrden(@PathVariable String id, Model model) {
        return ordenService.obtenerOrden(id)
                .map(orden -> {
                    model.addAttribute("orden", orden);
                    return "stock-manager/ordenes/detalle";
                })
                .orElse("redirect:/dashboard/stock/ordenes");
    }

    @GetMapping("")
    public String listarOrdenes(Model model) {
        model.addAttribute("ordenes", ordenService.encontrarTodas());
        return "stock-manager/ordenes/lista";
    }

    @PostMapping("/{id}/enviar")
    public String enviarOrden(@PathVariable String id) {
        ordenService.enviarOrden(id);
        return "redirect:/dashboard/stock/ordenes";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelarOrden(@PathVariable String id) {
        ordenService.cancelarOrden(id);
        return "redirect:/dashboard/stock/ordenes";
    }
}