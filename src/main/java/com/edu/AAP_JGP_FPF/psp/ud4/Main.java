package com.edu.AAP_JGP_FPF.psp.ud4;

import com.edu.AAP_JGP_FPF.psp.ud4.models.Film;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SWCrawler crawler = new SWCrawler();

        System.out.println("Probando conexión con SWAPI...");

        // Intentamos descargar el Episodio 4 (ID 1 en la base de datos)
        crawler.get("https://swapi.dev/api/films/1/", Film.class)
                .thenAccept(film -> {
                    // Si llegamos aquí, ¡funcionó!
                    System.out.println("¡ÉXITO!");
                    System.out.println("Película: " + film.title);
                    System.out.println("Director: " + film.episodeId);
                    System.out.println("Tiene " + film.characters.size() + " personajes en la lista.");
                })
                .exceptionally(ex -> {
                    // Si entramos aquí, algo falló
                    System.err.println("ERROR: " + ex.getMessage());
                    return null;
                })
                .join(); // El join() es necesario SOLO en el Main para que el programa no termine antes de recibir la respuesta
    }
}