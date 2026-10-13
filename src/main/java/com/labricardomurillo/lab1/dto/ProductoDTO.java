package com.labricardomurillo.lab1.dto;

import java.util.Set;

public record ProductoDTO (
    Long id,
    String nombre,
    String descripcion,
    double precio,
    Set<CategoriaResumenDTO> categorias
) {}

