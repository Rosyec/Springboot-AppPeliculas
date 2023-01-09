package com.example.consumirapi_rest.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pelicula implements Serializable{

    private Long id;

    private String nombre;

    private String genero;

    private String caratula;

    public Pelicula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    @Override
    public String toString() {
        return "Pelicula [id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", caratula=" + caratula + "]";
    }



    
    
}
