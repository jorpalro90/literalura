package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
 @JsonAlias("name") String nombreAutor,
 @JsonAlias("birth_year") Integer fechaNacimiento,
 @JsonAlias("death_year") Integer fechaMuerte) {
}
