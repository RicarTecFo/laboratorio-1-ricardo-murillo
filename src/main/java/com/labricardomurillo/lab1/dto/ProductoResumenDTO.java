package com.labricardomurillo.lab1.dto;

public record ProductoResumenDTO (

        Long id,
        String nombre,
        String descripcion,
        double precio,
        Long cantidad

) {

}
