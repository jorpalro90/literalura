package com.alura.literalura.main;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.models.DatosLibro;
import com.alura.literalura.models.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.exceptions.LiteraluraException;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);

    @Value("${gutendex.api.url}")
    private String urlBase;

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                **********************************************
                1. Buscar libro por título
                2. Listar libros registrados
                3. Listar autores registrados
                4. Listar autores vivos en un determinado año
                5. Listar libros por idioma
                6. Listar libros por género
                7. Listar géneros de libros
                8. Top 10 libros más descargados
                9. Estadísticas
                
                0. Salir
                **********************************************
                """;

            System.out.println(menu);

            try {
                System.out.print("Elige una opción: ");
                opcion = teclado.nextInt();
                teclado.nextLine();  // Limpiar el buffer del `Scanner`

                switch (opcion) {
                    case 1 -> buscarLibroTitulo();
                    case 2 -> listarLibrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivos();
                    case 5 -> listarLibrosPorIdioma();
                    case 6 -> listarLibrosPorGenero();
                    case 7 -> listarGenerosDeLibros();
                    case 8 -> top10LibrosMasDescargados();
                    case 9 -> estadisticas();
                    case 0 ->
                            System.out.println("********************************* \nCerrando la aplicación... \n*********************************");
                    default ->
                            System.out.println("********************************* \nLa opción ingresada es inválida \n*********************************");
                }
            } catch (InputMismatchException e) {
                System.out.println("*********************************");
                System.out.println("Entrada inválida. Por favor, ingresa un número válido.");
                System.out.println("*********************************");
                teclado.nextLine();  // Limpiar el buffer para evitar un bucle infinito
            }
        }
    }


    public void buscarLibroTitulo() {
        System.out.println("*********************************");
        System.out.println("Escribe el título del libro que deseas encontrar");
        System.out.println("*********************************");
        var tituloLibro = teclado.nextLine();
        try {
            String tituloCodificado = URLEncoder.encode(tituloLibro, StandardCharsets.UTF_8.toString());
            String url = urlBase + tituloCodificado;
            var json = consumoAPI.obtenerDatos(url);

            if (json == null || json.isEmpty()) {
                throw LiteraluraException.libroNoEncontrado(tituloLibro);
            }

            DatosLibro datosLibro = convierteDatos.obtenerDatos(json, DatosLibro.class);

            if (datosLibro.datosLibro().isEmpty()) {
                throw LiteraluraException.libroNoEncontrado(tituloLibro);
            }

            Libro libro = new Libro(datosLibro.datosLibro().get(0));
            libroRepository.save(libro);
            System.out.println("*********************************");
            System.out.println("Libro guardado: " + libro.getTitulo());
            System.out.println("*********************************");
        } catch (UnsupportedEncodingException e) {
            System.out.println("*********************************");
            System.out.println("Error al codificar el título: " + e.getMessage());
            System.out.println("*********************************");
        } catch (LiteraluraException e) {
            System.out.println("*********************************");
            System.out.println(e.getMessage());
            System.out.println("*********************************");
        }
    }

    public void listarLibrosRegistrados() {
        System.out.println("*********************************");
        List<Libro> libros = libroService.obtenerLibrosRegistrados();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> System.out.println(libro.getTitulo()));
        }
        System.out.println("*********************************");
    }

    public void listarAutoresRegistrados() {
        System.out.println("*********************************");
        try {
            List<AutorDTO> autores = libroService.obtenerAutoresRegistrados();
            autores.forEach(autor -> System.out.println(autor.getNombre()));
        } catch (LiteraluraException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("*********************************");
    }

    public void listarAutoresVivos() {
        System.out.println("*********************************");
        System.out.println("Ingresa el año para consultar los autores vivos");
        System.out.println("*********************************");
        int ano = teclado.nextInt();
        try {
            List<AutorDTO> autoresVivos = libroService.obtenerAutoresVivosPorAno(ano);
            autoresVivos.forEach(autor -> System.out.println(autor.getNombre()));
        } catch (LiteraluraException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("*********************************");
    }

    public void listarLibrosPorIdioma() {
        System.out.println("*********************************");
        List<String> idiomasDisponibles = libroService.obtenerIdiomasDistintos();
        System.out.println("Idiomas disponibles:");
        for (int i = 0; i < idiomasDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + idiomasDisponibles.get(i));
        }

        System.out.println("Selecciona el número del idioma que deseas buscar:");
        System.out.println("*********************************");
        int opcion = teclado.nextInt();
        if (opcion < 1 || opcion > idiomasDisponibles.size()) {
            System.out.println("*********************************");
            System.out.println("Opción inválida.");
            System.out.println("*********************************");
            return;
        }

        String idiomaSeleccionado = idiomasDisponibles.get(opcion - 1);
        try {
            List<Libro> librosPorIdioma = libroService.obtenerLibrosPorIdioma(idiomaSeleccionado);
            librosPorIdioma.forEach(libro -> System.out.println(libro.getTitulo()));
        } catch (Exception e) {
            System.out.println("*********************************");
            System.out.println(e.getMessage());
            System.out.println("*********************************");
        }
    }

    public void listarLibrosPorGenero() {
        // Obtener los géneros disponibles
        System.out.println("*********************************");
        List<String> generosDisponibles = libroService.obtenerGenerosDeLibros();
        System.out.println("Géneros disponibles:");
        System.out.println("*********************************");

        for (int i = 0; i < generosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + generosDisponibles.get(i));
        }

        System.out.println("*********************************");
        // Solicitar al usuario que seleccione un género
        System.out.println("Selecciona el número del género que deseas buscar:");
        System.out.println("*********************************");
        int opcion = teclado.nextInt();
        if (opcion < 1 || opcion > generosDisponibles.size()) {
            System.out.println("*********************************");
            System.out.println("Opción inválida.");
            System.out.println("*********************************");
            return;
        }

        // Obtener el género seleccionado
        String generoSeleccionado = generosDisponibles.get(opcion - 1);

        // Buscar los libros por el género seleccionado
        try {
            List<Libro> librosPorGenero = libroService.obtenerLibrosPorGenero(generoSeleccionado);
            if (librosPorGenero.isEmpty()) {
                System.out.println("*********************************");
                System.out.println("No se encontraron libros para el género especificado.");
                System.out.println("*********************************");
            } else {
                System.out.println("*********************************");
                System.out.println("Libros para el género: " + generoSeleccionado);
                System.out.println("*********************************");
                librosPorGenero.forEach(libro -> System.out.println(libro.getTitulo()));
            }
        } catch (Exception e) {
            System.out.println("*********************************");
            System.out.println("Error al obtener los libros: " + e.getMessage());
            System.out.println("*********************************");
        }
    }

    public void listarGenerosDeLibros() {
        System.out.println("*********************************");
        List<String> generos = libroService.obtenerGenerosDeLibros();
        if (generos.isEmpty()) {
            System.out.println("No se encontraron géneros en los libros registrados.");
        } else {
            System.out.println("Géneros de los libros almacenados:");
            generos.forEach(genero -> System.out.println(genero));
        }
        System.out.println("*********************************");
    }

    public void top10LibrosMasDescargados() {
        System.out.println("*********************************");
        System.out.println("Top 10 Libros más Descargados:");

        try {
            List<Libro> topLibros = libroService.obtenerTop10LibrosMasDescargados();

            if (topLibros.isEmpty()) {
                System.out.println("No se encontraron libros descargados.");
            } else {
                int rank = 1;
                for (Libro libro : topLibros) {
                    System.out.println(rank + ". " + libro.getTitulo() + " - Descargas: " + libro.getNumeroDescargas());
                    rank++;
                }
            }
        } catch (Exception e) {
            System.out.println("*********************************");
            System.out.println("Error al obtener los libros más descargados: " + e.getMessage());
            System.out.println("*********************************");
        }
    }

    public void estadisticas() {
        System.out.println("*********************************");
        System.out.println("Estadísticas:");

        try {
            // Obtener el total de libros registrados
            long totalLibros = libroService.obtenerTotalLibros();

            // Obtener el total de autores registrados
            long totalAutores = libroService.obtenerTotalAutores();

            // Obtener el género más frecuente
            String generoMasFrecuente = libroService.obtenerGeneroMasFrecuente();

            // Obtener la cantidad de libros por género
            List<String> generos = libroService.obtenerGenerosDeLibros();

            System.out.println("Total de libros registrados: " + totalLibros);
            System.out.println("Total de autores registrados: " + totalAutores);
            System.out.println("Género más frecuente: " + generoMasFrecuente);

            System.out.println("Cantidad de libros por género:");
            for (String genero : generos) {
                long cantidadPorGenero = libroService.obtenerCantidadDeLibrosPorGenero(genero);
                System.out.println(genero + ": " + cantidadPorGenero);
            }

        } catch (Exception e) {
            System.out.println("*********************************");
            System.out.println("Error al obtener las estadísticas: " + e.getMessage());
            System.out.println("*********************************");
        }
    }


}
