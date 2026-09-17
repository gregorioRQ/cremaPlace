package com.cremaplace.api.service;

import com.cremaplace.api.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final CatalogService catalogService;

    public OrderService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void createOrder(String userEmail, List<String> alfajoresIds, List<String> tartasIds) {
        if (alfajoresIds.size() < 2 || tartasIds.size() < 1) {
            throw new RuntimeException("Minimo de compra: 2 Alfajores y 1 Tarta");
        }
        Pedido pedido = new Pedido(String.valueOf(pedidos.size() + 1), userEmail, alfajoresIds, tartasIds);
        pedido.setTiempoEstimadoMinutos(calculateSuggestedTime(alfajoresIds, tartasIds));
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidosByUser(String email) {
        return pedidos.stream().filter(p -> p.getUserEmail().equals(email)).collect(Collectors.toList());
    }

    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    public List<Pedido> getPedidosByEstado(String estado) {
        return pedidos.stream().filter(p -> p.getEstado().equals(estado)).collect(Collectors.toList());
    }

    public List<Pedido> getPedidosParaCocina() {
        return pedidos.stream()
            .filter(p -> p.getEstado().equals("Pagado") || p.getEstado().equals("En Preparación"))
            .collect(Collectors.toList());
    }

    public void updateEstado(String id, String estado) {
        pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> p.setEstado(estado));
    }

    public void iniciarPreparacion(String id, int tiempoEstimadoManual) {
        pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> {
            p.setEstado("En Preparación");
            if (tiempoEstimadoManual > 0) {
                p.setTiempoEstimadoMinutos(tiempoEstimadoManual);
            }
        });
    }

    public void completarPedido(String id) {
        updateEstado(id, "Listo");
    }

    private int calculateSuggestedTime(List<String> alfajoresIds, List<String> tartasIds) {
        int alfajoresTime = alfajoresIds.stream()
            .mapToInt(id -> catalogService.getAllAlfajores().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(a -> a.getTiempoPreparacion())
                .orElse(0))
            .sum();
        
        int tartasTime = tartasIds.stream()
            .mapToInt(id -> catalogService.getAllTartas().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .map(t -> t.getTiempoPreparacion())
                .orElse(0))
            .sum();
        
        return alfajoresTime + tartasTime;
    }
}
