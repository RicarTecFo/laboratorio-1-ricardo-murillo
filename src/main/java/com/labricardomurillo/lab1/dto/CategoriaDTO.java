package com.labricardomurillo.lab1.dto;

import java.util.Set;

public record CategoriaDTO(
        Long id,
        String nombre,
        String descripcion,
        Set<ProductoResumenDTO> productos
) {

}