package com.cremaplace.api.controller;

import com.cremaplace.api.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrders(Authentication auth, Model model) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_COCINERO"))) {
            model.addAttribute("pedidos", orderService.getAllPedidos());
        } else {
            model.addAttribute("pedidos", orderService.getPedidosByUser(auth.getName()));
        }
        return "pedidos";
    }

    @PostMapping("/create")
    public String createOrder(Authentication auth, @RequestParam List<String> alfajores, @RequestParam List<String> tartas) {
        orderService.createOrder(auth.getName(), alfajores, tartas);
        return "redirect:/pedidos";
    }

    @PostMapping("/updateEstado")
    public String updateEstado(@RequestParam String id, @RequestParam String estado) {
        orderService.updateEstado(id, estado);
        return "redirect:/pedidos";
    }
}
