package com.labricardomurillo.lab1.service;


import com.labricardomurillo.lab1.mappers.ProductoMapper;
import com.labricardomurillo.lab1.model.Producto;
import com.labricardomurillo.lab1.repository.CategoriaRepository;
import com.labricardomurillo.lab1.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        log.info("Buscando el producto con identificador: ", id);
        return productoRepository.findById(id);
    }

    public Producto create(Producto producto) {
        producto.setId(null);
        producto.setCategoria(null);
        return productoRepository.save(producto);
    }

    @Transactional
    public Optional<Producto> update(Long id, Producto datos) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(datos.getNombre());
            producto.setDescripcion(datos.getDescripcion());
            producto.setPrecio(datos.getPrecio());
            producto.setCantidad(datos.getCantidad());
            return productoRepository.save(producto);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if(!productoRepository.existsById(id)){
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<Producto> agregarCategoria(Long productoId, Long categoriaId) {
        return productoRepository.findById(productoId).flatMap(producto ->
           categoriaRepository.findById(categoriaId).map(categoria -> {
               producto.setCategoria(categoria);
               return productoRepository.save(producto);
           }));
    }

    @Transactional
    public Optional<Producto> quitarCategoria(Long productoId, Long id) {
        return productoRepository.findById(productoId).map(producto -> {
                    producto.setCategoria(null);
                    return productoRepository.save(producto);
                });
    }

}
