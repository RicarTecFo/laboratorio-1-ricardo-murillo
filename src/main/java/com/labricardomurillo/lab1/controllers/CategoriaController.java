package com.labricardomurillo.lab1.controllers;

import com.labricardomurillo.lab1.dto.CategoriaDTO;
import com.labricardomurillo.lab1.mappers.CategoriaMapper;
import com.labricardomurillo.lab1.model.Categoria;
import com.labricardomurillo.lab1.service.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
@Slf4j
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper) {
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    @PreAuthorize("hasAuthority('categoria:leer')")
    @GetMapping
    public List<CategoriaDTO> findAll() {
        log.info("Listando todos las categorías");
        return categoriaMapper.toDTOList(categoriaService.findAll());
    }

    @PreAuthorize("hasAuthority('categoria:leer')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        log.info("Buscando categoría con id {}", id);
        return ResponseEntity.of(categoriaService.findById(id).map(categoriaMapper::toDTO));
    }

    @PreAuthorize("hasAuthority('categoria:escribir')")
    @PostMapping("/")
    public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO categoriaDTO) {
        log.info("Creando categoría");
        Categoria creado = categoriaService.create(categoriaMapper.toEntity(categoriaDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(categoriaMapper.toDTO(creado));
    }

    @PreAuthorize("hasAuthority('categoria:escribir')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        log.info("Actualizando categoría con id {}", id);
        return ResponseEntity.of(categoriaService.update(id, categoriaMapper.toEntity(categoriaDTO)).map(categoriaMapper::toDTO));
    }

    @PreAuthorize("hasAuthority('categoria:eliminar')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando categoría con id {}", id);
        return categoriaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('categoria:escribir')")
    @PutMapping("/{id}/categoria/{categoriaId}")
    public ResponseEntity<CategoriaDTO> agregarProducto(@PathVariable Long id, @PathVariable Long productoId) {
        log.info("Asignando producto {} a la categoría {}", productoId, id);
        return ResponseEntity.of(categoriaService.agregarProducto(id, productoId).map(categoriaMapper::toDTO));
    }

    @PreAuthorize("hasAuthority('categoria:escribir')")
    @DeleteMapping("/{id}/categoria/{categoriaId}")
    public ResponseEntity<CategoriaDTO> quitarProducto(@PathVariable Long id, @PathVariable Long productoId) {
        log.info("Quitando producto {} de la categoría {}", productoId, id);
        return ResponseEntity.of(categoriaService.quitarProducto(id, productoId).map(categoriaMapper::toDTO));
    }
}
