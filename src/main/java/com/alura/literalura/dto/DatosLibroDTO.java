package com.alura.literalura.dto;

import com.alura.literalura.models.DatosLibro;

import java.util.List;
import java.util.stream.Collectors;

public record DatosLibroDTO(List<LibroDTO> libros) {

    public DatosLibroDTO(List<LibroDTO> libros) {
        this.libros = libros;
    }

    @Override
    public List<LibroDTO> libros() {
        return libros;
    }

    @Override
    public String toString() {
        return "DatosLibroDTO{" +
                "libros= " + libros +
                '}';
    }

    public DatosLibroDTO convertirADatosLibroDTO(DatosLibro datosLibro) {
        List<LibroDTO> libros = datosLibro.datosLibro().stream().map(resultsLibro -> {
            List<AutorDTO> autores = resultsLibro.autor().stream()
                    .map(autor -> new AutorDTO(autor.nombreAutor(),autor.fechaNacimiento(), autor.fechaMuerte()))
                    .collect(Collectors.toList());
            return new LibroDTO(
                    resultsLibro.id(),
                    resultsLibro.titulo(),
                    autores,
                    resultsLibro.genero(),
                    resultsLibro.idioma(),
                    resultsLibro.numeroDescargas(),
                    resultsLibro.formatos()
            );
        }).collect(Collectors.toList());

        return new DatosLibroDTO(libros);
    }
}
