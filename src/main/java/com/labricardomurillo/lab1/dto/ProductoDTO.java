package com.labricardomurillo.lab1.dto;

public record ProductoDTO (
    Long id,
    String nombre,
    String descripcion,
    double precio,
    Long cantidad,
    CategoriaResumenDTO categoria
) {}