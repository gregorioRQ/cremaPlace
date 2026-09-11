package com.cremaplace.api.service;

import com.cremaplace.api.model.Alfajor;
import com.cremaplace.api.model.Tarta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CatalogService {
    private final List<Alfajor> alfajores = new ArrayList<>();
    private final List<Tarta> tartas = new ArrayList<>();
    private final InventoryService inventoryService;

    public CatalogService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

        // Datos de prueba
        alfajores.add(new Alfajor("a1", "Chocolate", "Simple", 10.0, 5));
        alfajores.add(new Alfajor("a2", "Dulce de Leche", "Triple", 15.0, 10));
        
        tartas.add(new Tarta("t1", "Frutilla", 100.0, 30));
        tartas.add(new Tarta("t2", "Lemon Pie", 90.0, 25));
    }

    public List<Alfajor> getAllAlfajores() { return alfajores; }
    public List<Tarta> getAllTartas() { return tartas; }

    public void addAlfajor(String sabor, String tamano, double precio, int tiempoPreparacion) {
        String id = "a_" + UUID.randomUUID().toString().substring(0, 5);
        Alfajor alfajor = new Alfajor(id, sabor, tamano, precio, tiempoPreparacion);
        alfajores.add(alfajor);
        inventoryService.addInventoryItem(id, "Alfajor " + sabor + " " + tamano, 0);
    }

    public void deleteAlfajor(String id) {
        alfajores.removeIf(a -> a.getId().equals(id));
        inventoryService.removeInventoryItem(id);
    }

    public void addTarta(String sabor, double precioUnidad, int tiempoPreparacion) {
        String id = "t_" + UUID.randomUUID().toString().substring(0, 5);
        Tarta tarta = new Tarta(id, sabor, precioUnidad, tiempoPreparacion);
        tartas.add(tarta);
        inventoryService.addInventoryItem(id, "Tarta " + sabor + " Unidad", 0);
    }

    public void deleteTarta(String id) {
        tartas.removeIf(t -> t.getId().equals(id));
        inventoryService.removeInventoryItem(id);
    }
}
