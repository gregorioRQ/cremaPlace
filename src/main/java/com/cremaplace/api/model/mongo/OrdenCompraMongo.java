package com.cremaplace.api.model.mongo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una orden de compra a un proveedor
 */
@Document(collection = "ordenes_compra")
public class OrdenCompraMongo {
    public enum EstadoOrden {
        BORRADOR, ENVIADA, PARCIAL, COMPLETADA, CANCELADA
    }

    @Id
    private String id;
    private String numeroOrden; // Legible para humanos (ej: OC-2026-0001)
    private String proveedorId; // Referencia a ProveedorMongo.id
    private LocalDateTime fecha;
    private EstadoOrden estado;
    private List<ItemOrdenMongo> items;
    private Double total;

    // Clase interna para los items de la orden
    public static class ItemOrdenMongo {
        private String ingredienteId; // Referencia a Ingrediente.ingredientId (en memoria)
        private Integer cantidadSolicitada;
        private Integer cantidadRecibida = 0;
        private Double precioUnitario;

        // Constructores
        public ItemOrdenMongo() {}

        public ItemOrdenMongo(String ingredienteId, Integer cantidadSolicitada, Double precioUnitario) {
            this.ingredienteId = ingredienteId;
            this.cantidadSolicitada = cantidadSolicitada;
            this.precioUnitario = precioUnitario;
        }

        // Getters y Setters
        public String getIngredienteId() {
            return ingredienteId;
        }

        public void setIngredienteId(String ingredienteId) {
            this.ingredienteId = ingredienteId;
        }

        public Integer getCantidadSolicitada() {
            return cantidadSolicitada;
        }

        public void setCantidadSolicitada(Integer cantidadSolicitada) {
            this.cantidadSolicitada = cantidadSolicitada;
        }

        public Integer getCantidadRecibida() {
            return cantidadRecibida;
        }

        public void setCantidadRecibida(Integer cantidadRecibida) {
            this.cantidadRecibida = cantidadRecibida;
        }

        public Double getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(Double precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public Double getSubtotal() {
            if (cantidadSolicitada != null && precioUnitario != null) {
                return cantidadSolicitada * precioUnitario;
            }
            return 0.0;
        }
    }

    // Constructores
    public OrdenCompraMongo() {
        this.items = new ArrayList<>();
    }

    public OrdenCompraMongo(String proveedorId, LocalDateTime fecha) {
        this();
        this.proveedorId = proveedorId;
        this.fecha = fecha;
        this.estado = EstadoOrden.BORRADOR;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public List<ItemOrdenMongo> getItems() {
        return items;
    }

    public void setItems(List<ItemOrdenMongo> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Calcula el total basado en los items
     */
    public void calcularTotal() {
        if (items != null) {
            this.total = items.stream()
                .mapToDouble(ItemOrdenMongo::getSubtotal)
                .sum();
        } else {
            this.total = 0.0;
        }
    }
}