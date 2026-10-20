package com.cremaplace.api.model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private String id;
    private String userEmail;
    private String clienteNombre;
    private double total;
    private LocalDateTime fechaCreacion;
    private List<OrderItem> items; // detalle de productos
    private List<String> alfajoresIds; // Simple (ids)
    private List<String> tartasIds; // Half (ids)
    private String estado; // Pendiente, Pagado, En Preparación, Listo, Entregado, Cancelado
    private int tiempoEstimadoMinutos;
    private boolean cancelado = false;
    private String cancelReason;
    private boolean stockWarning = false; // transient, set by service

    // Constructor usado por el servicio al crear pedido
    public Pedido(String id, String userEmail, String clienteNombre, double total,
                  LocalDateTime fechaCreacion, List<OrderItem> items,
                  List<String> alfajoresIds, List<String> tartasIds) {
        this.id = id;
        this.userEmail = userEmail;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
        this.items = items;
        this.alfajoresIds = alfajoresIds;
        this.tartasIds = tartasIds;
        this.estado = "Pendiente";
        this.tiempoEstimadoMinutos = 0;
    }

    // Mantener constructor antiguo por compatibilidad (puede no usarse)
    public Pedido(String id, String userEmail, List<String> alfajoresIds, List<String> tartasIds) {
        this.id = id;
        this.userEmail = userEmail;
        this.alfajoresIds = alfajoresIds;
        this.tartasIds = tartasIds;
        this.estado = "Pendiente";
        this.tiempoEstimadoMinutos = 0;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public List<String> getAlfajoresIds() { return alfajoresIds; }
    public void setAlfajoresIds(List<String> alfajoresIds) { this.alfajoresIds = alfajoresIds; }
    public List<String> getTartasIds() { return tartasIds; }
    public void setTartasIds(List<String> tartasIds) { this.tartasIds = tartasIds; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getTiempoEstimadoMinutos() { return tiempoEstimadoMinutos; }
    public void setTiempoEstimadoMinutos(int tiempoEstimadoMinutos) { this.tiempoEstimadoMinutos = tiempoEstimadoMinutos; }
    public boolean isCancelado() { return cancelado; }
    public void setCancelado(boolean cancelado) { this.cancelado = cancelado; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public boolean isStockWarning() { return stockWarning; }
    public void setStockWarning(boolean stockWarning) { this.stockWarning = stockWarning; }
}
