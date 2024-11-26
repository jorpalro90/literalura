package com.alura.literalura.repository;

import com.alura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
