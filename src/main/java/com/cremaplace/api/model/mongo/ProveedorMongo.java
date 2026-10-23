package com.cremaplace.api.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa un proveedor de ingredientes y materias primas
 */
@Document(collection = "proveedores")
public class ProveedorMongo {
    @Id
    private String id;
    private String nombre;
    private String contacto; // email/teléfono
    private boolean activo;

    // Constructores
    public ProveedorMongo() {}

    public ProveedorMongo(String nombre, String contacto, boolean activo) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.activo = activo;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}