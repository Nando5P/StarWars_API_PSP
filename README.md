# StarWars API PSP

Este proyecto es una práctica desarrollada por alumnos de **2º de Desarrollo de Aplicaciones Multiplataforma (DAM)** para la asignatura de **Programación de Servicios y Procesos (PSP)**.

El objetivo principal es demostrar el uso de **APIs externas** y la implementación de **sistemas de hilos y concurrencia** en Java para realizar peticiones HTTP eficientes y no bloqueantes.

## 👥 Autores

*   **Fernando Parga Fernández**
*   **Javier González Prados**
*   **Alejandro Azpeitia Blanco**

---

## 🚀 Descripción del Proyecto

La aplicación es un "Crawler" de datos del universo Star Wars que utiliza la API pública [SWAPI (Star Wars API)](https://swapi.info/).

El programa permite al usuario seleccionar una de las películas de la saga y, automáticamente:
1.  Descarga la información básica de la película.
2.  Identifica todos los personajes, planetas, especies, naves y vehículos asociados.
3.  Realiza **peticiones asíncronas en paralelo** para descargar los detalles de cada uno de estos recursos.
4.  Genera un informe detallado en consola con códigos de colores ANSI.
5.  Exporta los datos recopilados a un archivo **JSON** para su posterior consulta o integración (por ejemplo, con MockAPI).

## 🛠️ Tecnologías y Conceptos Clave

*   **Java 11+**: Uso de `HttpClient` moderno.
*   **Programación Asíncrona**: Uso intensivo de `CompletableFuture` para gestionar múltiples tareas en paralelo sin bloquear el hilo principal.
*   **Concurrencia**: `ExecutorService` con un pool de hilos para optimizar las descargas simultáneas.
*   **Consumo de API REST**: Peticiones HTTP GET.
*   **Procesamiento JSON**:
    *   `Jackson`: Para deserializar las respuestas de la API a objetos Java.
    *   `Gson`: Para serializar el informe final a un archivo JSON legible.
*   **Patrón de Diseño**: Uso de caché (Memoización) para evitar descargar la misma URL múltiples veces.

## ⚙️ Cómo Funciona

1.  **Inicio**: El usuario selecciona un episodio (1-6).
2.  **Crawl**: El sistema lanza un proceso asíncrono que:
    *   Obtiene la película.
    *   Obtiene la lista de personajes.
    *   Analiza qué naves y vehículos aparecen, cruzando datos de la película y de los personajes.
    *   Lanza peticiones paralelas para obtener detalles de Planetas, Especies, Naves y Vehículos.
3.  **Sincronización**: Espera a que todas las descargas paralelas finalicen (`CompletableFuture.allOf`).
4.  **Resultado**:
    *   Muestra el informe por pantalla.
    *   Genera un archivo `film_report_[ID].json` en la raíz del proyecto.

## 📋 Requisitos

*   JDK 11 o superior.
*   Conexión a Internet (para acceder a swapi.info).
*   Maven (para gestión de dependencias: Jackson, Gson).

---
*Práctica realizada para el ciclo formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma.*