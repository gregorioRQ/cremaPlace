package com.cremaplace.api.model;

public class Alfajor {
    private String id;
    private String sabor;
    private String tamano; // Simple o Triple
    private double precio;
    private int tiempoPreparacion; // Minutos

    public Alfajor(String id, String sabor, String tamano, double precio, int tiempoPreparacion) {
        this.id = id;
        this.sabor = sabor;
        this.tamano = tamano;
        this.precio = precio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getId() { return id; }
    public String getSabor() { return sabor; }
    public String getTamano() { return tamano; }
    public double getPrecio() { return precio; }
    public int getTiempoPreparacion() { return tiempoPreparacion; }
}
