package com.cremaplace.api.service;

import com.cremaplace.api.model.mongo.ProveedorMongo;
import com.cremaplace.api.repository.mongo.ProveedorMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de proveedores (solo lectura para STOCK_MANAGER)
 */
@Service
public class ProveedorService {
    private final ProveedorMongoRepository proveedorRepository;

    public ProveedorService(ProveedorMongoRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * Obtiene todos los proveedores activos
     */
    public List<ProveedorMongo> findAll() {
        return proveedorRepository.findAll();
    }

    /**
     * Obtiene un proveedor por su ID
     */
    public Optional<ProveedorMongo> findById(String id) {
        return proveedorRepository.findById(id);
    }
}