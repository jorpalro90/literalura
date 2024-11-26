package com.alura.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    // Constructor vacío
    public Genero() {
    }

    // Constructor con parámetros
    public Genero(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return nombre;
    }
}
