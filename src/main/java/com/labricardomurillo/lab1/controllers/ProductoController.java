package com.labricardomurillo.lab1.controllers;

import com.labricardomurillo.lab1.dto.ProductoDTO;
import com.labricardomurillo.lab1.mappers.ProductoMapper;
import com.labricardomurillo.lab1.model.Producto;
import com.labricardomurillo.lab1.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
    }

    @PreAuthorize("hasAuthority('producto:leer')")
    @GetMapping
    public List<ProductoDTO> findAll() {
        log.info("Listando todos los productos");
        return productoMapper.toDto(productoService.findAll());
    }

    @PreAuthorize("hasAuthority('producto:leer')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        log.info("Buscando producto con id {}", id);
        return ResponseEntity.of(productoService.findById(id).map(productoMapper::toDto));
    }

    @PreAuthorize("hasAuthority('producto:escribir')")
    @PostMapping("/")
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO productoDTO) {
        log.info("Creando producto");
        Producto creado = productoService.create(productoMapper.toEntity(productoDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(productoMapper.toDto(creado));
    }

    @PreAuthorize("hasAuthority('producto:escribir')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        log.info("Actualizando producto con id {}", id);
        return ResponseEntity.of(productoService.update(id, productoMapper.toEntity(productoDTO)).map(productoMapper::toDto));
    }

    @PreAuthorize("hasAuthority('producto:eliminar')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando producto con id {}", id);
        return productoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('producto:escribir')")
    @PutMapping("/{id}/categoria/{categoriaId}")
    public ResponseEntity<ProductoDTO> agregarCategoria(@PathVariable Long id, @PathVariable Long categoriaId) {
        log.info("Asignando categoría {} al producto {}", categoriaId, id);
        return ResponseEntity.of(productoService.agregarCategoria(id, categoriaId).map(productoMapper::toDto));
    }

    @PreAuthorize("hasAuthority('categoria:escribir')")
    @DeleteMapping("/{id}/producto/{productoId}")
    public ResponseEntity<ProductoDTO> quitarCategoria(@PathVariable Long id, @PathVariable Long productoId) {
        log.info("Quitando producto {} de la categoría {}", productoId, id);
        return ResponseEntity.of(productoService.quitarCategoria(id, productoId).map(productoMapper::toDto));
    }
}
