package com.edu.AAP_JGP_FPF.psp.ud4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edu.AAP_JGP_FPF.psp.ud4.models.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SWCrawler {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ExecutorService executor;

    private final Map<String, CompletableFuture<?>> cache = new ConcurrentHashMap<>();

    /**
     * CONSTRUCTOR 
     * Creará 10 hilos usando Executor para solicitar la petición de información a la API
     */
    public SWCrawler() {
        this.executor = Executors.newFixedThreadPool(10);

        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(15))
                .executor(executor)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Método principal que descarga la película y la información detallada de la misma
     * @param filmId
     */
    public CompletableFuture<FilmReport> crawlFilm(int filmId) {
        String url = "https://swapi.info/api/films/" + filmId + "/";

        return get(url, Film.class).thenCompose(film -> {
            System.out.println("Película: " + film.title + ". Descargando personajes para analizar sus naves...");

            return getAll(film.characters, Person.class).thenCompose(people -> {

                java.util.Set<String> allStarshipUrls = new java.util.HashSet<>(film.starships);
                people.forEach(p -> allStarshipUrls.addAll(p.starships));

                java.util.Set<String> allVehicleUrls = new java.util.HashSet<>(film.vehicles);
                people.forEach(p -> allVehicleUrls.addAll(p.vehicles));

                System.out.println("  -> Detectadas " + allStarshipUrls.size() + " naves y " + allVehicleUrls.size() + " vehículos totales.");

                var planetsFuture = getAll(film.planets, Planet.class);
                var speciesFuture = getAll(film.species, Species.class);
                var starshipsFuture = getAll(new java.util.ArrayList<>(allStarshipUrls), Starship.class);
                var vehiclesFuture = getAll(new java.util.ArrayList<>(allVehicleUrls), Vehicle.class);

                return CompletableFuture.allOf(planetsFuture, speciesFuture, starshipsFuture, vehiclesFuture)
                        .thenApply(v -> new FilmReport(
                                film,
                                planetsFuture.join(),
                                speciesFuture.join(),
                                people,
                                starshipsFuture.join(),
                                vehiclesFuture.join()
                        ));
            });
        });
    }

    /**
     * Método auxiliar para descargar múltiples recursos en paralelo.
     * Convierte una lista de URLs en una lista de objetos completados.
     *
     * @param urls Lista de URLs a descargar.
     * @param clazz Clase del objeto esperado.
     * @return Un CompletableFuture que contiene la lista de objetos descargados.
     * @param <T> Tipo del objeto.
     */
      private <T> CompletableFuture<List<T>> getAll(List<String> urls, Class<T> clazz) {
        List<CompletableFuture<T>> futures = urls.stream()
                .map(url -> get(url, clazz))
                .collect(Collectors.toList());

        // Convertimos List<Future<T>> a Future<List<T>>
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    /**
     * Realiza una petición GET asíncrona a una URL específica.
     * Implementa caché para evitar descargas duplicadas y deserializa la respuesta JSON.
     *
     * @param url URL del recurso a descargar.
     * @param clazz Clase a la que se debe mapear el JSON de respuesta.
     * @return Un CompletableFuture con el objeto deserializado.
     * @param <T> Tipo del objeto a retornar.
     */
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> get(String url, Class<T> clazz) {
        String secureUrl = url.replace("http://", "https://");

        // Si la URL ya está en caché, devolvemos el Futuro existente
        return (CompletableFuture<T>) cache.computeIfAbsent(secureUrl, k -> {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(k)).GET().build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return objectMapper.readValue(response.body(), clazz);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        });
    }

    /**
     * Cierra el ExecutorService liberando los recursos del pool de hilos.
     */
    public void shutdown() {
        executor.shutdown();
    }
}
