package com.alura.literalura.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 500)
    private String titulo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_genero",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos;


    private String idioma;
    private Double numeroDescargas;
    private String portada;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autorList;

    public Libro() {
    }

    public Libro(ResultsLibro datosLibro) {
        this.id = datosLibro.id();
        this.idioma = String.valueOf(datosLibro.idioma());
        this.numeroDescargas = datosLibro.numeroDescargas();
        this.portada = datosLibro.formatos().get("image/jpeg");
        this.titulo = datosLibro.titulo();

        // Mapear géneros
        this.generos = datosLibro.genero().stream()
                .map(g -> {
                    Genero genero = new Genero();
                    genero.setNombre(g); // Verifica que g tenga un valor válido
                    return genero;
                })
                .collect(Collectors.toList());


        // Mapear autores
        this.autorList = datosLibro.autor().stream()
                .map(original -> {
                    Autor autor = new Autor(original.nombreAutor(), original.fechaNacimiento(), original.fechaMuerte());
                    autor.setLibro(this);
                    return autor;
                })
                .collect(Collectors.toList());
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    @Override
    public String toString() {
        String autores = autorList.stream()
                .map(Autor::getNombreAutor)
                .collect(Collectors.joining(","));
        String generos = this.generos.stream()
                .map(Genero::getNombre)
                .collect(Collectors.joining(","));
        return String.format("Libro: Id= %d, Titulo= '%s', Genero= '%s', Idioma= '%s', Numero de descargas= %.1f," +
                        " Portada= '%s', Autores= '%s'}",
                id, titulo, generos, idioma, numeroDescargas, portada, autores);
    }
}
