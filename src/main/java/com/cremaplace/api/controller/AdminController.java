package com.cremaplace.api.controller;

import com.cremaplace.api.model.mongo.ProveedorMongo;
import com.cremaplace.api.service.CatalogService;
import com.cremaplace.api.service.InventoryService;
import com.cremaplace.api.service.OrderService;
import com.cremaplace.api.service.ProveedorService;
import com.cremaplace.api.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final ProveedorService proveedorService;

    public AdminController(UserService userService, CatalogService catalogService,
                           InventoryService inventoryService, OrderService orderService,
                           ProveedorService proveedorService) {
        this.userService = userService;
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.proveedorService = proveedorService;
    }

    // Main admin dashboard - redirects to users section by default
    @GetMapping("")
    public String adminDashboard() {
        return "redirect:/dashboard/admin/users";
    }

    // User management page
    @GetMapping("/users")
    public String manageUsers(Model model, @RequestParam(required = false) String search) {
        model.addAttribute("users", userService.searchUsersByUsername(search));
        model.addAttribute("search", search);
        return "admin/users";
    }

    // Catalog management page
    @GetMapping("/catalog")
    public String manageCatalog(Model model) {
        model.addAttribute("alfajores", catalogService.getAllAlfajores());
        model.addAttribute("tartas", catalogService.getAllTartas());
        return "admin/catalog";
    }

    // Orders management page (placeholder - implement similar to users/catalog)
    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("pedidos", orderService.getAllPedidos());
        return "admin/orders";
    }

    // Inventory management page (placeholder - implement similar to users/catalog)
    @GetMapping("/inventory")
    public String manageInventory(Model model) {
        model.addAttribute("inventory", inventoryService.getAllInventory());
        return "admin/inventory";
    }

    // Supplier management page
    @GetMapping("/proveedores")
    public String manageSuppliers(Model model) {
        model.addAttribute("proveedores", proveedorService.findAll());
        return "admin/proveedores/lista";
    }

    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable String id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/dashboard/admin/users";
    }

    @PostMapping("/users/{id}/unlock")
    public String unlockUserAccount(@PathVariable String id) {
        userService.unlockUserAccount(id);
        return "redirect:/dashboard/admin/users";
    }

    @PostMapping("/catalog/alfajor/add")
    public String addAlfajor(@RequestParam String sabor, @RequestParam String tamano,
                             @RequestParam double precio, @RequestParam int tiempoPreparacion) {
        catalogService.addAlfajor(sabor, tamano, precio, tiempoPreparacion);
        return "redirect:/dashboard/admin/catalog";
    }

    @PostMapping("/catalog/alfajor/delete")
    public String deleteAlfajor(@RequestParam String id) {
        catalogService.deleteAlfajor(id);
        return "redirect:/dashboard/admin/catalog";
    }

    @PostMapping("/catalog/tarta/add")
    public String addTarta(@RequestParam String sabor, @RequestParam double precioUnidad,
                           @RequestParam int tiempoPreparacion) {
        catalogService.addTarta(sabor, precioUnidad, tiempoPreparacion);
        return "redirect:/dashboard/admin/catalog";
    }

    @PostMapping("/catalog/tarta/delete")
    public String deleteTarta(@RequestParam String id) {
        catalogService.deleteTarta(id);
        return "redirect:/dashboard/admin/catalog";
    }

    @PostMapping("/catalog/alfajor/update")
    public String updateAlfajor(@RequestParam String id,
                               @RequestParam String sabor,
                               @RequestParam String tamano,
                               @RequestParam double precio,
                               @RequestParam int tiempoPreparacion) {
        catalogService.updateAlfajor(id, sabor, tamano, precio, tiempoPreparacion);
        return "redirect:/dashboard/admin/catalog";
    }

    @PostMapping("/catalog/tarta/update")
    public String updateTarta(@RequestParam String id,
                             @RequestParam String sabor,
                             @RequestParam double precioUnidad,
                             @RequestParam int tiempoPreparacion) {
        catalogService.updateTarta(id, sabor, precioUnidad, tiempoPreparacion);
        return "redirect:/dashboard/admin/catalog";
    }

    @PostMapping("/inventory/update")
    public String updateStock(@RequestParam String productId, @RequestParam int stock) {
        inventoryService.updateStock(productId, stock);
        return "redirect:/dashboard/admin/inventory";
    }

    // Supplier management endpoints
    @PostMapping("/proveedores/nuevo")
    public String createSupplier(
            @RequestParam String nombre,
            @RequestParam String contacto,
            @RequestParam(defaultValue = "false") boolean activo) {
        ProveedorMongo proveedor = new ProveedorMongo(nombre, contacto, activo);
        proveedorService.save(proveedor);
        return "redirect:/dashboard/admin/proveedores";
    }

    @PostMapping("/proveedores/editar/{id}")
    public String updateSupplier(
            @PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String contacto,
            @RequestParam(defaultValue = "false") boolean activo) {
        ProveedorMongo proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado: " + id));
        proveedor.setNombre(nombre);
        proveedor.setContacto(contacto);
        proveedor.setActivo(activo);
        proveedorService.save(proveedor);
        return "redirect:/dashboard/admin/proveedores";
    }

    @PostMapping("/proveedores/eliminar/{id}")
    public String deleteSupplier(@PathVariable String id) {
        proveedorService.deleteById(id);
        return "redirect:/dashboard/admin/proveedores";
    }

    @PostMapping("/pedidos/updateEstado")
    public String updateOrderEstado(@RequestParam String id, @RequestParam String estado) {
        orderService.updateEstado(id, estado);
        return "redirect:/dashboard/admin/orders";
    }
}
