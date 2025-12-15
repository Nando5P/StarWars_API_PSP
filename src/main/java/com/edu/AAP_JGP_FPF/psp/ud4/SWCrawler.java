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

    // CACHÉ: Guarda URLs que ya hemos pedido o estamos pidiendo
    private final Map<String, CompletableFuture<?>> cache = new ConcurrentHashMap<>();

    public SWCrawler() {
        // Límite de 10 hilos simultáneos para ser educados con la API
        this.executor = Executors.newFixedThreadPool(10);

        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(15))
                .executor(executor) // Usamos nuestro pool de hilos
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Método PRINCIPAL: Descarga la película y luego todo lo relacionado
     */
    public CompletableFuture<FilmReport> crawlFilm(int filmId) {
        String url = "https://swapi.dev/api/films/" + filmId + "/";

        // FASE 1: Descargar Película
        return get(url, Film.class).thenCompose(film -> {
            System.out.println("Película: " + film.title + ". Descargando personajes para analizar sus naves...");

            // FASE 2: Descargar Personajes primero
            // Necesitamos sus datos para saber qué naves "extra" hay que buscar
            return getAll(film.characters, Person.class).thenCompose(people -> {

                // Recolectamos TODAS las URLs de naves (las de la peli + las de los personajes)
                // Usamos un Set para evitar duplicados, aunque la caché ya protege, esto ahorra procesamiento
                java.util.Set<String> allStarshipUrls = new java.util.HashSet<>(film.starships);
                people.forEach(p -> allStarshipUrls.addAll(p.starships));

                // Lo mismo para vehículos
                java.util.Set<String> allVehicleUrls = new java.util.HashSet<>(film.vehicles);
                people.forEach(p -> allVehicleUrls.addAll(p.vehicles));

                System.out.println("  -> Detectadas " + allStarshipUrls.size() + " naves y " + allVehicleUrls.size() + " vehículos totales.");

                // FASE 3: Descargar todo lo demás en paralelo
                // (Planetas, Especies, y la lista COMPLETA de Naves y Vehículos)
                var planetsFuture = getAll(film.planets, Planet.class);
                var speciesFuture = getAll(film.species, Species.class);
                var starshipsFuture = getAll(new java.util.ArrayList<>(allStarshipUrls), Starship.class);
                var vehiclesFuture = getAll(new java.util.ArrayList<>(allVehicleUrls), Vehicle.class);

                return CompletableFuture.allOf(planetsFuture, speciesFuture, starshipsFuture, vehiclesFuture)
                        .thenApply(v -> new FilmReport(
                                film,
                                planetsFuture.join(),
                                speciesFuture.join(),
                                people, // Pasamos la lista de personas que ya descargamos en Fase 2
                                starshipsFuture.join(),
                                vehiclesFuture.join()
                        ));
            });
        });
    }

    // Helper para descargar una LISTA de URLs en paralelo
    private <T> CompletableFuture<List<T>> getAll(List<String> urls, Class<T> clazz) {
        List<CompletableFuture<T>> futures = urls.stream()
                .map(url -> get(url, clazz))
                .collect(Collectors.toList());

        // Truco estándar para convertir List<Future<T>> a Future<List<T>>
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }

    // Método individual con Caché
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> get(String url, Class<T> clazz) {
        String secureUrl = url.replace("http://", "https://");

        // Si la URL ya está en caché, devolvemos el Futuro existente (evita duplicados)
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

    // Para cerrar los hilos al terminar
    public void shutdown() {
        executor.shutdown();
    }
}
