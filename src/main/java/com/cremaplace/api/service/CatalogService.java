package com.cremaplace.api.service;

import com.cremaplace.api.model.Alfajor;
import com.cremaplace.api.model.Tarta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    private final List<Alfajor> alfajores = new ArrayList<>();
    private final List<Tarta> tartas = new ArrayList<>();

    public CatalogService() {
        // Datos de prueba
        alfajores.add(new Alfajor("a1", "Chocolate", "Simple", 10.0, 5));
        alfajores.add(new Alfajor("a2", "Dulce de Leche", "Triple", 15.0, 10));
        
        tartas.add(new Tarta("t1", "Frutilla", 100.0, 30));
        tartas.add(new Tarta("t2", "Lemon Pie", 90.0, 25));
    }

    public List<Alfajor> getAllAlfajores() { return alfajores; }
    public List<Tarta> getAllTartas() { return tartas; }
}
