package com.edu.AAP_JGP_FPF.psp.ud4;

import com.edu.AAP_JGP_FPF.psp.ud4.models.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilmReport {
    private final Film film;
    private final List<Planet> planets;
    private final List<Species> species;
    private final List<Person> people;

    // Mapas para búsqueda rápida: Url -> Objeto
    // Esto es clave: nos permite buscar "¿Cómo se llama la nave de la URL X?" instantáneamente
    private final Map<String, Starship> starshipMap;
    private final Map<String, Vehicle> vehicleMap;

    public FilmReport(Film film, List<Planet> planets, List<Species> species,
                      List<Person> people, List<Starship> starships, List<Vehicle> vehicles) {
        this.film = film;
        this.planets = planets;
        this.species = species;
        this.people = people;
        // Convertimos las listas de naves/vehículos a Mapas usando su URL como clave
        this.starshipMap = starships.stream().collect(Collectors.toMap(s -> s.url, s -> s));
        this.vehicleMap = vehicles.stream().collect(Collectors.toMap(v -> v.url, v -> v));
    }

    public void print() {
        System.out.println("\n=== INFORME COMPLETO: " + film.title.toUpperCase() + " ===");

        // Planetas y Especies igual que antes...
        System.out.println("\n-- Planetas (" + planets.size() + ") --");
        planets.forEach(p -> System.out.println("   * " + p.name)); // Simplificado para ahorrar espacio

        System.out.println("\n-- Especies (" + species.size() + ") --");
        species.forEach(s -> System.out.println("   * " + s.name));

        // PERSONAJES
        System.out.println("\n-- Personajes (" + people.size() + ") y su historial de pilotaje --");
        for (Person p : people) {
            System.out.println(" > " + p.name);

            // MOSTRAR NAVES (Todas las que pilota en su vida)
            if (!p.starships.isEmpty()) {
                List<String> shipNames = p.starships.stream()
                        // Buscamos en el mapa. Si por algún error de red falta, ponemos "Descarga fallida"
                        .map(url -> starshipMap.containsKey(url) ? starshipMap.get(url).name : "Info no disponible")
                        .collect(Collectors.toList());

                System.out.println("     Naves: " + shipNames);
            }

            // MOSTRAR VEHÍCULOS (Todos los que pilota en su vida)
            if (!p.vehicles.isEmpty()) {
                List<String> vehicleNames = p.vehicles.stream()
                        .map(url -> vehicleMap.containsKey(url) ? vehicleMap.get(url).name : "Info no disponible")
                        .collect(Collectors.toList());

                System.out.println("     Vehículos: " + vehicleNames);
            }
        }
    }
}