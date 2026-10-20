package com.cremaplace.api.model;

public class OrderItem {
    private String productId;
    private String nombre;
    private String tipo; // "ALFAJOR" | "TARTA"
    private int cantidad;
    private double precioUnitario;

    // Constructors
    public OrderItem() {}

    public OrderItem(String productId, String nombre, String tipo, int cantidad, double precioUnitario) {
        this.productId = productId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}