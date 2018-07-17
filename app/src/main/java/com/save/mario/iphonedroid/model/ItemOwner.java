package com.save.mario.iphonedroid.model;

public class ItemOwner {

    private String id;
    private String nombre;
    private String descipcion;

    public ItemOwner(){
    }
    public ItemOwner(String id, String nombre, String descipcion) {
        this.id = id;
        this.nombre = nombre;
        this.descipcion = descipcion;
    }

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

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }
}
