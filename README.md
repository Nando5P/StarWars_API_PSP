StarWars API Project - PSP 🌌
Este repositorio contiene una aplicación desarrollada en Java para la asignatura de Programación de Servicios y Procesos (PSP). La aplicación se conecta a la API pública de Star Wars (SWAPI) para obtener, procesar y mostrar información sobre el universo de Star Wars de forma dinámica.

📋 Descripción
El objetivo principal de este proyecto es demostrar el manejo de comunicaciones en red, consumo de servicios REST y procesamiento de datos en formato JSON utilizando Java.

Funcionalidades principales:

📡 Conexión HTTP: Realiza peticiones GET a los endpoints de la SWAPI.

🧬 Parseo JSON: Convierte las respuestas del servidor en objetos Java utilizables.

🧵 Multihilo (Opcional): Gestión de peticiones asíncronas para no bloquear el hilo principal.

🔍 Búsqueda: Permite buscar información sobre [Películas de las que mostrará varios detalles].

⚙️ Arquitectura y Flujo
El funcionamiento básico de la aplicación sigue el modelo cliente-servidor, donde nuestra aplicación Java actúa como cliente HTTP solicitando recursos a la API.

El usuario solicita información (ej. "A New Hope").

La aplicación construye la URL y envía una petición HTTP.

SWAPI responde con un JSON crudo.

La aplicación deserializa el JSON y lo muestra formateado en consola/interfaz.

🛠️ Tecnologías Utilizadas
Lenguaje: Java [Versión, ej. 17 o 21]

API: SWAPI (The Star Wars API)

Gestor de Dependencias: [Maven / Gradle / Ninguno]

Librerías de Terceros:

Procesamiento JSON: [Gson / Jackson / org.json]

Cliente HTTP: [HttpURLConnection / Java 11 HttpClient / OkHttp]

🚀 Instalación y Ejecución
Sigue estos pasos para ejecutar el proyecto en tu entorno local:

1. Clonar el repositorio
Bash

git clone https://github.com/Nando5P/StarWars_API_PSP.git
cd StarWars_API_PSP
2. Abrir en tu IDE
Abre el proyecto en tu IDE favorito (IntelliJ IDEA, Eclipse, NetBeans).

Si el proyecto usa Maven, asegúrate de que se descarguen las dependencias (pom.xml).

3. Ejecutar
Localiza la clase principal Main.java (o App.java) y ejecútala.

Bash

# Si usas terminal y tienes Maven:
mvn clean compile exec:java
📂 Estructura del Proyecto
Plaintext

StarWars_API_PSP/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/nando5p/
│   │   │   │   ├── models/       # Clases POJO (Character, Planet, etc.)
│   │   │   │   ├── network/      # Cliente HTTP y gestión de API
│   │   │   │   ├── utils/        # Parseadores JSON
│   │   │   │   └── Main.java     # Punto de entrada
│   │   └── resources/
├── pom.xml (o build.gradle)
└── README.md
📄 Ejemplos de Uso
Al ejecutar el programa, verás un menú similar a este (ejemplo):

Plaintext

--- STAR WARS DATA CRAWLER ---
1. A NEW HOPE (1977)
2. THE EMPIRE STRIKES BACK (1980)
3. RETURN OF THE JEDI (1983)
4. ....

Introduce el ID de la película: 1

(Se muestra la información detallada de la película, personajes, planetas, especies, etc)
   
✒️ Autores
Fernando Parge Fernández
Alejandro Azpeitia Blanco
Javier González Prados

Este proyecto es de carácter educativo para el ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM).
