package com.cremaplace.api.service;

import com.cremaplace.api.model.mongo.OrdenCompraMongo;
import com.cremaplace.api.repository.mongo.OrdenCompraMongoRepository;
import com.cremaplace.api.service.ProveedorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para gestión de órdenes de compra
 */
@Service
public class OrdenCompraService {
    private final OrdenCompraMongoRepository ordenRepository;
    private final ProveedorService proveedorService;

    public OrdenCompraService(OrdenCompraMongoRepository ordenRepository, ProveedorService proveedorService) {
        this.ordenRepository = ordenRepository;
        this.proveedorService = proveedorService;
    }

    /**
     * Crea una nueva orden de compra
     */
    public OrdenCompraMongo crearOrden(OrdenCompraMongo orden) {
        // Generar número de orden si no tiene uno
        if (orden.getNumeroOrden() == null || orden.getNumeroOrden().isEmpty()) {
            orden.setNumeroOrden(generarNumeroOrden());
        }
        orden.setEstado(OrdenCompraMongo.EstadoOrden.BORRADOR);
        orden.calcularTotal();
        return ordenRepository.save(orden);
    }

    /**
     * Obtiene una orden por su ID
     */
    public Optional<OrdenCompraMongo> obtenerOrden(String id) {
        return ordenRepository.findById(id);
    }

    /**
     * Envía una orden de compra (cambia estado de BORRADOR a ENVIADA)
     */
    public void enviarOrden(String id) {
        OrdenCompraMongo orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + id));
        orden.setEstado(OrdenCompraMongo.EstadoOrden.ENVIADA);
        ordenRepository.save(orden);
    }

    /**
     * Cancela una orden de compra
     */
    public void cancelarOrden(String id) {
        OrdenCompraMongo orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + id));
        orden.setEstado(OrdenCompraMongo.EstadoOrden.CANCELADA);
        ordenRepository.save(orden);
    }

    /**
     * Obtiene todas las órdenes
     */
    public java.util.List<OrdenCompraMongo> encontrarTodas() {
        return ordenRepository.findAll();
    }

    /**
     * Obtiene órdenes por estado
     */
    public java.util.List<OrdenCompraMongo> encontrarPorEstado(OrdenCompraMongo.EstadoOrden estado) {
        return ordenRepository.findByEstado(estado);
    }

    /**
     * Genera un número de orden único (simplificado)
     */
    private String generarNumeroOrden() {
        // En un entorno real, esto sería más robusto (usando secuencias, etc.)
        long count = ordenRepository.count();
        return String.format("OC-%04d", count + 1);
    }
}