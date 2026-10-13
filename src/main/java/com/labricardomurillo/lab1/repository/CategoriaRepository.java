package com.labricardomurillo.lab1.repository;

import com.labricardomurillo.lab1.model.Categoria;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Override
    @EntityGraph(attributePaths = "productos")
    Optional<Categoria> findById(Long id);
}
