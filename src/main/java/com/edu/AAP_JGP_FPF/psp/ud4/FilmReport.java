package com.edu.AAP_JGP_FPF.psp.ud4;

import com.edu.AAP_JGP_FPF.psp.ud4.models.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilmReport {

    // --- CÓDIGOS ANSI PARA COLORES EN CONSOLA ---
    // Nota: Funcionan en la mayoría de terminales modernas (IntelliJ, VSCode, Linux, Mac, Win10+)
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BOLD = "\u001B[1m";
    // -------------------------------------------

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

        // Título en negrita
        System.out.println("\n" + border);
        System.out.println(ANSI_BOLD + "       STAR WARS: EPISODIO " + film.episodeId + " - " + film.title.toUpperCase() + ANSI_RESET);
        System.out.println(border);

        System.out.println("Director:       " + film.director);
        System.out.println("Productores:    " + film.producer);
        System.out.println("Lanzamiento:    " + film.releaseDate);

        System.out.println("\n" + border);
        // --- AQUÍ LLAMAMOS AL EFECTO ESPECIAL ---
        printOpeningCrawlWithEffect(film.openingCrawl);
        // ----------------------------------------
        System.out.println(border);

        // --- RESTO DEL INFORME ---

        System.out.println("\n" + ANSI_BOLD + "-- Planetas (" + planets.size() + ") --" + ANSI_RESET);
        planets.forEach(p -> System.out.println("   * " + p.name));

        System.out.println("\n" + ANSI_BOLD + "-- Especies (" + species.size() + ") --" + ANSI_RESET);
        species.forEach(s -> System.out.println("   * " + s.name));

        System.out.println("\n" + ANSI_BOLD + "-- Personajes (" + people.size() + ") y su historial de pilotaje --" + ANSI_RESET);
        for (Person p : people) {
            System.out.println(" > " + ANSI_BOLD + p.name + ANSI_RESET);

            if (!p.starships.isEmpty()) {
                List<String> shipNames = p.starships.stream()
                        .map(url -> starshipMap.containsKey(url) ? starshipMap.get(url).name : "Info no disponible")
                        .collect(Collectors.toList());
                System.out.println("     Naves: " + shipNames);
            }

            if (!p.vehicles.isEmpty()) {
                List<String> vehicleNames = p.vehicles.stream()
                        .map(url -> vehicleMap.containsKey(url) ? vehicleMap.get(url).name : "Info no disponible")
                        .collect(Collectors.toList());
                System.out.println("     Vehículos: " + vehicleNames);
            }
        }
        System.out.println("\n" + border);
    }

    // --- MÉTODO AUXILIAR PARA EL EFECTO DE CINE ---
    private void printOpeningCrawlWithEffect(String crawlText) {
        // 1. Dividir el texto en líneas (soporta saltos de Windows \r\n y Linux \n)
        String[] lines = crawlText.split("\\r?\\n");

        System.out.println(); // Un poco de aire antes de empezar

        // Activamos el color AMARILLO
        System.out.print(ANSI_YELLOW);

        try {
            for (String line : lines) {
                // Truco matemático para intentar centrar el texto (asumiendo consola de 80 chars de ancho)
                int padding = Math.max(0, (80 - line.length()) / 2);
                String centeredLine = " ".repeat(padding) + line;

                // Imprimimos la línea centrada
                System.out.println(centeredLine);

                // PAUSA DRAMÁTICA (700 milisegundos entre líneas)
                Thread.sleep(700);
            }
        } catch (InterruptedException e) {
            // Si alguien interrumpe el programa mientras duerme
            Thread.currentThread().interrupt();
            System.err.println(ANSI_RESET + "\n[!] Efecto de texto interrumpido.");
        } finally {
            // IMPORTANTE: Volver al color normal, pase lo que pase
            System.out.print(ANSI_RESET);
        }
        System.out.println();
    }
}