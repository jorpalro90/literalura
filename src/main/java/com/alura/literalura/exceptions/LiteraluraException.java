package com.alura.literalura.exceptions;

public class LiteraluraException extends RuntimeException {

    public LiteraluraException(String message) {
        super(message);
    }

    // Método estático para libro no encontrado
    public static LiteraluraException libroNoEncontrado(String titulo) {
        return new LiteraluraException("Libro no encontrado: " + titulo);
    }

    // Método estático para autor no encontrado
    public static LiteraluraException autorNoEncontrado(String nombreAutor) {
        return new LiteraluraException("Autor no encontrado: " + nombreAutor);
    }

    // Método estático para autor vivo no encontrado
    public static LiteraluraException autorVivoNoEncontrado(int ano) {
        return new LiteraluraException("No se encontraron autores vivos en el año: " + ano);
    }
}
