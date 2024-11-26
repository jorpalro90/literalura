package com.alura.literalura.service;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.exceptions.LiteraluraException;
import com.alura.literalura.models.Autor;
import com.alura.literalura.models.Genero;
import com.alura.literalura.models.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public List<Libro> obtenerLibrosRegistrados() {
        return libroRepository.obtenerLibrosRegistrados();
    }

    public List<AutorDTO> obtenerAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            throw LiteraluraException.autorNoEncontrado("No se encontraron autores regisrados");
        }
        return autores.stream()
                .map(autor -> new AutorDTO(autor.getNombreAutor(), autor.getFechaNacimiento(), autor.getFechaMuerte()))
                .collect(Collectors.toList());
    }

    public List<AutorDTO> obtenerAutoresVivosPorAno(int ano) {
        List<Autor> autores = autorRepository.findAll();
        List<AutorDTO> autoresVivos = autores.stream()
                .filter(autor -> autor.getFechaNacimiento() <= ano &&
                        (autor.getFechaMuerte() == null || autor.getFechaMuerte() > ano))
                .map(autor -> new AutorDTO(autor.getNombreAutor(), autor.getFechaNacimiento(), autor.getFechaMuerte()))
                .collect(Collectors.toList());
        if (autoresVivos.isEmpty()) {
            throw LiteraluraException.autorVivoNoEncontrado(ano);
        }
        return autoresVivos;
    }

    public List<Libro> obtenerLibrosPorIdioma(String idioma) throws Exception {
        System.out.println("Buscando libros en el idioma: " + idioma); // Log de depuración

        // Verificar si el idioma existe en la base de datos
        List<String> idiomasDisponibles = libroRepository.findDistinctIdiomas();
        if (!idiomasDisponibles.contains(idioma.toLowerCase())) {
            throw new Exception("El idioma especificado no está registrado en la base de datos: " + idioma);
        }

        // Buscar libros filtrando por idioma exacto (ignora mayúsculas/minúsculas)
        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            throw new Exception("No se encontraron libros en el idioma especificado: " + idioma);
        }
        return libros;
    }

    public List<String> obtenerIdiomasDistintos() {
        return libroRepository.findDistinctIdiomas();  // Llamamos al método del repositorio
    }

    // En el servicio (LibroService)
    public List<Libro> obtenerLibrosPorGenero(String genero) {
        // Filtrar los libros por género, aquí supongo que el género está en un campo `genero`
        return libroRepository.findLibrosByGeneros(Collections.singletonList(genero));
    }

    public List<String> obtenerGenerosDeLibros() {
        List<Libro> libros = libroRepository.findAll();  // O usa un filtro si lo necesitas
        List<String> generos = new ArrayList<>();
        for (Libro libro : libros) {
            for (Genero genero : libro.getGeneros()) {
                if (!generos.contains(genero.getNombre())) {  // Para evitar duplicados
                    generos.add(genero.getNombre());
                }
            }
        }
        return generos;
    }

    public long obtenerTotalLibros() {
        return libroRepository.count();  // Cuenta todos los libros en la base de datos
    }

    public long obtenerTotalAutores() {
        return autorRepository.count();  // Cuenta todos los autores en la base de datos
    }


    public String obtenerGeneroMasFrecuente() {
        List<Libro> libros = libroRepository.findAll();
        return libros.stream()
                .flatMap(libro -> libro.getGeneros().stream()) // Asegúrate de tener un método getGeneros()
                .map(Genero::getNombre)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay géneros registrados");
    }

    public long obtenerCantidadDeLibrosPorGenero(String genero) {
        return libroRepository.countByNombreGenero(genero);
    }

    public List<Libro> obtenerTop10LibrosMasDescargados() {
        return libroRepository.findAll()
                .stream()
                .sorted((l1, l2) -> Long.compare(Math.round(l2.getNumeroDescargas()), Math.round(l1.getNumeroDescargas())))
                .limit(10)
                .collect(Collectors.toList());
    }

}





