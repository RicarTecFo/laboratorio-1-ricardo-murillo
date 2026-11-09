package com.labricardomurillo.lab1.repository;

import com.labricardomurillo.lab1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{}
