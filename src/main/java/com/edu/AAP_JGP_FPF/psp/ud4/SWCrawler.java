package com.edu.AAP_JGP_FPF.psp.ud4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edu.AAP_JGP_FPF.psp.ud4.models.Film; // Importamos para pruebas puntuales
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SWCrawler {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public SWCrawler() {
        // 1. Configuramos el cliente HTTP
        // Usamos un timeout de 10s para que no se quede colgado eternamente si falla la red
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // 2. Inicializamos el convertidor de JSON
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Método GENÉRICO para descargar cualquier cosa (Film, Person, Planet...)
     * @param url La dirección web a descargar
     * @param clazz La clase a la que queremos convertir el JSON (ej: Film.class)
     * @return Un Futuro que contendrá el objeto ya convertido
     */
    public <T> CompletableFuture<T> get(String url, Class<T> clazz) {
        // A veces SWAPI devuelve "http", pero es mejor forzar "https" para evitar redirecciones
        String secureUrl = url.replace("http://", "https://");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(secureUrl))
                .header("Accept", "application/json") // Pedimos amablemente JSON
                .GET()
                .build();

        // Enviamos la petición de forma ASÍNCRONA
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    // Verificamos si la petición fue exitosa (Código 200)
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("Error HTTP " + response.statusCode() + " en " + secureUrl);
                    }
                    try {
                        // AQUÍ ocurre la magia: Texto JSON -> Objeto Java
                        return objectMapper.readValue(response.body(), clazz);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al leer el JSON de " + secureUrl, e);
                    }
                });
    }
}
