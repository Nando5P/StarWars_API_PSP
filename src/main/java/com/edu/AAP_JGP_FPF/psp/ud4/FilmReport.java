package com.edu.AAP_JGP_FPF.psp.ud4;

import com.edu.AAP_JGP_FPF.psp.ud4.models.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilmReport {

    // Colores para destacar secciones
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private final Film film;
    private final List<Planet> planets;
    private final List<Species> species;
    private final List<Person> people;
    private final Map<String, Starship> starshipMap;
    private final Map<String, Vehicle> vehicleMap;

    public FilmReport(Film film, List<Planet> planets, List<Species> species,
                      List<Person> people, List<Starship> starships, List<Vehicle> vehicles) {
        this.film = film;
        this.planets = planets;
        this.species = species;
        this.people = people;
        this.starshipMap = starships.stream().collect(Collectors.toMap(s -> s.url, s -> s));
        this.vehicleMap = vehicles.stream().collect(Collectors.toMap(v -> v.url, v -> v));
    }

    public void print() {
        String border = "=".repeat(70);

        System.out.println("\n" + border);
        System.out.println(ANSI_BOLD + "       STAR WARS: EPISODIO " + film.episodeId + " - " + film.title.toUpperCase() + ANSI_RESET);
        System.out.println(border);

        System.out.println("Director:       " + film.director);
        System.out.println("Productores:    " + film.producer);
        System.out.println("Lanzamiento:    " + film.releaseDate);

        // Efecto del Opening Crawl (mantenemos tu efecto chulo)
        printOpeningCrawlWithEffect(film.openingCrawl);
        System.out.println(border);

        // --- PLANETAS ---
        System.out.println("\n" + ANSI_BOLD + ">>> PLANETAS (" + planets.size() + ")" + ANSI_RESET);
        for (Planet p : planets) {
            System.out.println(ANSI_CYAN + " • " + p.name + ANSI_RESET);
            System.out.printf("     Clima: %-15s | Terreno: %s%n", p.climate, p.terrain);
            System.out.printf("     Diámetro: %-12s | Población: %s%n", p.diameter, p.population);
            System.out.printf("     Rotación: %-12s | Órbita: %s días%n", p.rotationPeriod, p.orbitalPeriod);
        }

        // --- ESPECIES ---
        System.out.println("\n" + ANSI_BOLD + ">>> ESPECIES (" + species.size() + ")" + ANSI_RESET);
        for (Species s : species) {
            System.out.println(ANSI_CYAN + " • " + s.name + ANSI_RESET);
            System.out.printf("     Clase: %-15s | Lengua: %s%n", s.classification, s.language);
            System.out.printf("     Altura media: %-8s | Vida media: %s años%n", s.averageHeight, s.averageLifespan);
            System.out.printf("     Rasgos: Piel(%s), Ojos(%s)%n", s.skinColors, s.eyeColors);
        }

        // --- PERSONAJES (Y SUS MÁQUINAS) ---
        System.out.println("\n" + ANSI_BOLD + ">>> PERSONAJES (" + people.size() + ")" + ANSI_RESET);
        for (Person p : people) {
            System.out.println(ANSI_BOLD + " > " + p.name.toUpperCase() + ANSI_RESET);
            // Detalles del personaje
            System.out.printf("   [Ficha] Nacimiento: %-8s | Género: %-8s | Altura: %s | Peso: %s%n",
                    p.birthYear, p.gender, p.height, p.mass);
            System.out.printf("           Pelo: %-12s | Ojos: %s%n", p.hairColor, p.eyeColor);

            // NAVES DETALLADAS
            if (!p.starships.isEmpty()) {
                System.out.println(ANSI_YELLOW + "   [Naves Pilotadas]:" + ANSI_RESET);
                for (String url : p.starships) {
                    if (starshipMap.containsKey(url)) {
                        Starship s = starshipMap.get(url);
                        System.out.println("     - " + s.name);
                        System.out.println("       Modelo: " + s.model);
                        System.out.println("       Clase: " + s.starshipClass + " | Fabr: " + s.manufacturer);
                        System.out.println("       Velocidad: " + s.maxAtmospheringSpeed + " | Hiperimpulsor: " + s.hyperdriveRating);
                        System.out.println("       Coste: " + s.costInCredits + " créditos");
                    } else {
                        System.out.println("     - Datos no disponibles (Error de carga)");
                    }
                }
            }

            // VEHÍCULOS DETALLADOS
            if (!p.vehicles.isEmpty()) {
                System.out.println(ANSI_YELLOW + "   [Vehículos Pilotados]:" + ANSI_RESET);
                for (String url : p.vehicles) {
                    if (vehicleMap.containsKey(url)) {
                        Vehicle v = vehicleMap.get(url);
                        System.out.println("     - " + v.name);
                        System.out.println("       Modelo: " + v.model);
                        System.out.println("       Clase: " + v.vehicleClass + " | Fabr: " + v.manufacturer);
                        System.out.println("       Coste: " + v.costInCredits + " créditos");
                    } else {
                        System.out.println("     - Datos no disponibles");
                    }
                }
            }
            System.out.println(); // Separador entre personajes
        }
        System.out.println(border);
    }

    private void printOpeningCrawlWithEffect(String crawlText) {
        String[] lines = crawlText.split("\\r?\\n");
        System.out.println();
        System.out.print(ANSI_YELLOW);
        try {
            for (String line : lines) {
                int padding = Math.max(0, (80 - line.length()) / 2);
                String centeredLine = " ".repeat(padding) + line;
                System.out.println(centeredLine);
                Thread.sleep(700);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.print(ANSI_RESET);
        }
        System.out.println();
    }
}