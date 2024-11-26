package com.alura.literalura.dto;

import java.util.List;
import java.util.Map;

public record LibroDTO(
        Long id,
        String titulo,
        List<AutorDTO> autores,
        List<String> generos,
        List<String> idiomas,
        Double numeroDescargas,
        Map<String, String> formatos) {

    @Override
    public String toString() {
        return "LibroDTO{" +
                "Id= " + id +
                ", Titulo= '" + titulo + '\'' +
                ", Autor= " + autores +
                ", Genero= " + generos +
                ", Idiomas= " + idiomas +
                ", Numero de descargas= " + numeroDescargas +
                ", Formatos= " + formatos +
                '}';
    }
}


