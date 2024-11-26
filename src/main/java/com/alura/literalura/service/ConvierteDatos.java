package com.alura.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            objectMapper.findAndRegisterModules();
            return objectMapper.readValue(json, clase);
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir JSON a " + clase.getSimpleName(), e);
        }
    }
}
