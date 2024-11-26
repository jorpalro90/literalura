package com.alura.literalura.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.io.IOException;

public class ConsumoAPI {

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en la respuesta de la API: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al hacer la solicitud a la API", e);
        }

        String json = response.body();
        if (json == null || json.isEmpty()) {
            throw new RuntimeException("La respuesta de la API está vacía.");
        }
        return json;
    }
}
