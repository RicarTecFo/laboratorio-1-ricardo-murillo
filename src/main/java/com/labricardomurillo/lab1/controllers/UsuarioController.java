package com.labricardomurillo.lab1.controllers;

import com.labricardomurillo.lab1.dto.UsuarioResumenDTO;
import com.labricardomurillo.lab1.model.Usuario;
import com.labricardomurillo.lab1.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Slf4j
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public List<UsuarioResumenDTO> findAll() {
        List<UsuarioResumenDTO> usuarios = new ArrayList<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            usuarios.add(new UsuarioResumenDTO(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getEmail()
            ));
        }
        return usuarios;
    }

    public record UsuarioResumenDTO(Long id, String username, String email) {}
}
