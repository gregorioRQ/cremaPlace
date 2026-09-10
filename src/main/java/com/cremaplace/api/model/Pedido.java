package com.cremaplace.api.model;

import java.util.List;

public class Pedido {
    private String id;
    private String userEmail;
    private List<String> alfajoresIds; // Simple
    private List<String> tartasIds; // Half
    private String estado; // Pendiente, Pagado, En Preparación, Listo, Entregado

    public Pedido(String id, String userEmail, List<String> alfajoresIds, List<String> tartasIds) {
        this.id = id;
        this.userEmail = userEmail;
        this.alfajoresIds = alfajoresIds;
        this.tartasIds = tartasIds;
        this.estado = "Pendiente";
    }

    public String getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public List<String> getAlfajoresIds() { return alfajoresIds; }
    public List<String> getTartasIds() { return tartasIds; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
