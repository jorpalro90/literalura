package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultsLibro(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("subjects") List<String> genero,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Double numeroDescargas,
        @JsonAlias("formats") Map<String, String> formatos) {
}
