package com.cremaplace.api.model;

public class Tarta {
    private String id;
    private String sabor;
    private double precioUnidad;
    private int tiempoPreparacion; // Minutos

    public Tarta(String id, String sabor, double precioUnidad, int tiempoPreparacion) {
        this.id = id;
        this.sabor = sabor;
        this.precioUnidad = precioUnidad;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getId() { return id; }
    public String getSabor() { return sabor; }
    public double getPrecioUnidad() { return precioUnidad; }
    public int getTiempoPreparacion() { return tiempoPreparacion; }
}
