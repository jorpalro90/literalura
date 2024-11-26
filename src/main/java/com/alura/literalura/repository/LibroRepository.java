package com.alura.literalura.repository;

import com.alura.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdiomaIgnoreCase(String idioma);

    @Query("SELECT l FROM Libro l ")
    List<Libro> obtenerLibrosRegistrados();

    @Query("SELECT DISTINCT LOWER(l.idioma) FROM Libro l")
    List<String> findDistinctIdiomas();

    // Consulta personalizada para obtener los libros y sus géneros
    @Query("SELECT l FROM Libro l JOIN l.generos g WHERE g.nombre IN :generos")
    List<Libro> findLibrosByGeneros(List<String> generos);

    @Query("SELECT COUNT(l) FROM Libro l JOIN l.generos g WHERE g.nombre = :nombreGenero")
    long countByNombreGenero(@Param("nombreGenero") String nombreGenero);

    // Consulta para obtener los 10 libros más descargados
    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC")
    List<Libro> findTop10ByOrderByNumeroDescargasDesc();

    @Query("SELECT g.nombre, COUNT(l) FROM Libro l JOIN l.generos g GROUP BY g.nombre ORDER BY COUNT(l) DESC")
    List<Object[]> findGenerosMasFrecuentes();


}