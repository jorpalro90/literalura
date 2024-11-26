package com.alura.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombreAutor;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @ManyToOne
    private Libro libro;

    public Autor() {

    }

    public Autor(String nombreAutor, Integer fechaNacimiento, Integer fechaMuerte) {
        this.nombreAutor = nombreAutor;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaMuerte = fechaMuerte;

    }

    public Autor(DatosAutor datosAutor) {
        this.fechaMuerte = datosAutor.fechaMuerte();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.nombreAutor = datosAutor.nombreAutor();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }
}

