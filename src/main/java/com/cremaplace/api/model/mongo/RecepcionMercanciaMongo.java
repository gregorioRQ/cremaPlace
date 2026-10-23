package com.cremaplace.api.model.mongo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una recepción de mercancía asociada a una orden de compra
 */
@Document(collection = "recepciones_mercancia")
public class RecepcionMercanciaMongo {
    @Id
    private String id;
    private String ordenCompraId; // Referencia a OrdenCompraMongo.id
    private LocalDateTime fechaRecepcion;
    private List<ItemRecepcionMongo> itemsRecibidos;
    private String observaciones;

    // Clase interna para los items recibidos
    public static class ItemRecepcionMongo {
        private String ingredienteId; // Referencia a Ingrediente.ingredientId (en memoria)
        private Integer cantidad;
        private String numeroLote; // Opcional
        private LocalDate fechaVencimiento; // Opcional

        // Constructores
        public ItemRecepcionMongo() {}

        public ItemRecepcionMongo(String ingredienteId, Integer cantidad) {
            this.ingredienteId = ingredienteId;
            this.cantidad = cantidad;
        }

        // Getters y Setters
        public String getIngredienteId() {
            return ingredienteId;
        }

        public void setIngredienteId(String ingredienteId) {
            this.ingredienteId = ingredienteId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public String getNumeroLote() {
            return numeroLote;
        }

        public void setNumeroLote(String numeroLote) {
            this.numeroLote = numeroLote;
        }

        public LocalDate getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }
    }

    // Constructores
    public RecepcionMercanciaMongo() {
        this.itemsRecibidos = new ArrayList<>();
    }

    public RecepcionMercanciaMongo(String ordenCompraId, LocalDateTime fechaRecepcion) {
        this();
        this.ordenCompraId = ordenCompraId;
        this.fechaRecepcion = fechaRecepcion;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdenCompraId() {
        return ordenCompraId;
    }

    public void setOrdenCompraId(String ordenCompraId) {
        this.ordenCompraId = ordenCompraId;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public List<ItemRecepcionMongo> getItemsRecibidos() {
        return itemsRecibidos;
    }

    public void setItemsRecibidos(List<ItemRecepcionMongo> itemsRecibidos) {
        this.itemsRecibidos = itemsRecibidos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}