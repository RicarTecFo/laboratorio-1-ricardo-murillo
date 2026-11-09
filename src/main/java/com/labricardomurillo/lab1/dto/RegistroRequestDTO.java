package com.labricardomurillo.lab1.dto;

public record RegistroRequestDTO(
        Long id,
        String username,
        String email,
        String password
) {
}
