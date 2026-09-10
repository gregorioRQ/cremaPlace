package com.cremaplace.api.service;

import com.cremaplace.api.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final List<Pedido> pedidos = new ArrayList<>();

    public void createOrder(String userEmail, List<String> alfajoresIds, List<String> tartasIds) {
        if (alfajoresIds.size() < 2 || tartasIds.size() < 1) {
            throw new RuntimeException("Minimo de compra: 2 Alfajores y 1 Tarta");
        }
        pedidos.add(new Pedido(String.valueOf(pedidos.size() + 1), userEmail, alfajoresIds, tartasIds));
    }

    public List<Pedido> getPedidosByUser(String email) {
        return pedidos.stream().filter(p -> p.getUserEmail().equals(email)).collect(Collectors.toList());
    }

    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    public void updateEstado(String id, String estado) {
        pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().ifPresent(p -> p.setEstado(estado));
    }
}
