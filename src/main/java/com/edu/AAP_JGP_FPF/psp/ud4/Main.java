package com.edu.AAP_JGP_FPF.psp.ud4;

import com.fasterxml.jackson.databind.ObjectMapper;         
import com.fasterxml.jackson.databind.SerializationFeature; 
import java.io.File;                                        
import java.io.IOException;                                 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SWCrawler crawler = new SWCrawler();
        Scanner scanner = new Scanner(System.in);

        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        boolean continuar = true;

        System.out.println("=========================================");
        System.out.println("   STAR WARS DATA CRAWLER - SWAPI.DEV    ");
        System.out.println("=========================================");

        while (continuar) {
            System.out.println("\nElige una película para analizar y exportar:");
            System.out.println("1. A New Hope (1977)");
            System.out.println("2. The Empire Strikes Back (1980)");
            System.out.println("3. Return of the Jedi (1983)");
            System.out.println("4. The Phantom Menace (1999)");
            System.out.println("5. Attack of the Clones (2002)");
            System.out.println("6. Revenge of the Sith (2005)");
            System.out.println("0. Salir");
            System.out.print("\nIntroduce el ID de la película: ");

            try {
                String entrada = scanner.nextLine();
                int filmId = Integer.parseInt(entrada);

                if (filmId == 0) {
                    continuar = false;
                    System.out.println("Cerrando el sistema...");
                } else if (filmId >= 1 && filmId <= 6) {

                    long start = System.currentTimeMillis();
                    System.out.println("\n>>> Iniciando descarga para la película ID " + filmId + "...");
                    System.out.println(">>> Por favor espera, descargando datos y generando JSON...");

                    crawler.crawlFilm(filmId)
                            .thenAccept(report -> {                               
                                report.print();

                                try {
                                    String fileName = "starwars_episode_" + filmId + ".json";
                                    File file = new File(fileName);

                                    jsonMapper.writeValue(file, report);

                                    System.out.println("\n[✔] JSON generado correctamente: " + file.getAbsolutePath());
                                    System.out.println("    (Listo para subir a MockAPI)");

                                } catch (IOException e) {
                                    System.err.println("\n[X] Error guardando el JSON: " + e.getMessage());
                                }

                                long time = System.currentTimeMillis() - start;
                                System.out.println("[!] Proceso total finalizado en " + time + "ms");
                            })
                            .exceptionally(e -> {
                                System.err.println("Error obteniendo datos: " + e.getMessage());
                                return null;
                            })
                            .join();

                } else {
                    System.out.println("⚠️ ID no válido. Por favor introduce un número del 1 al 6.");
                }

            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, introduce solo números.");
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        }

        crawler.shutdown();
        scanner.close();
        System.out.println("Crawler finalizado. ¡Que la fuerza te acompañe!");
    }
}
