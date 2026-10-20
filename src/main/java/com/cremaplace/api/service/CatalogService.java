package com.cremaplace.api.service;

import com.cremaplace.api.model.Alfajor;
import com.cremaplace.api.model.Tarta;
import com.cremaplace.api.repository.AlfajorRepository;
import com.cremaplace.api.repository.TartaRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    private final AlfajorRepository alfajorRepository;
    private final TartaRepository tartaRepository;
    private final InventoryService inventoryService;

    public CatalogService(AlfajorRepository alfajorRepository, TartaRepository tartaRepository, InventoryService inventoryService) {
        this.alfajorRepository = alfajorRepository;
        this.tartaRepository = tartaRepository;
        this.inventoryService = inventoryService;
    }

    public Iterable<Alfajor> getAllAlfajores() { return alfajorRepository.findAll(); }
    public Iterable<Tarta> getAllTartas() { return tartaRepository.findAll(); }

    public Alfajor getAlfajorById(String id) {
        return alfajorRepository.findById(id).orElse(null);
    }

    public Tarta getTartaById(String id) {
        return tartaRepository.findById(id).orElse(null);
    }

    public void addAlfajor(String sabor, String tamano, double precio, int tiempoPreparacion) {
        Alfajor alfajor = new Alfajor(null, sabor, tamano, precio, tiempoPreparacion);
        Alfajor savedAlfajor = alfajorRepository.save(alfajor);
        inventoryService.addInventoryItem(savedAlfajor.getId(), "Alfajor " + sabor + " " + tamano, 0);
    }

    public void deleteAlfajor(String id) {
        alfajorRepository.deleteById(id);
        inventoryService.removeInventoryItem(id);
    }

    public void addTarta(String sabor, double precioUnidad, int tiempoPreparacion) {
        Tarta tarta = new Tarta(null, sabor, precioUnidad, tiempoPreparacion);
        Tarta savedTarta = tartaRepository.save(tarta);
        inventoryService.addInventoryItem(savedTarta.getId(), "Tarta " + sabor + " Unidad", 0);
    }

    public void deleteTarta(String id) {
        tartaRepository.deleteById(id);
        inventoryService.removeInventoryItem(id);
    }

    public void updateAlfajor(String id, String sabor, String tamano, double precio, int tiempoPreparacion) {
        Alfajor alfajor = alfajorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Alfajor not found"));
        alfajor.setSabor(sabor);
        alfajor.setTamano(tamano);
        alfajor.setPrecio(precio);
        alfajor.setTiempoPreparacion(tiempoPreparacion);
        alfajorRepository.save(alfajor);
    }

    public void updateTarta(String id, String sabor, double precioUnidad, int tiempoPreparacion) {
        Tarta tarta = tartaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarta not found"));
        tarta.setSabor(sabor);
        tarta.setPrecioUnidad(precioUnidad);
        tarta.setTiempoPreparacion(tiempoPreparacion);
        tartaRepository.save(tarta);
    }
}
