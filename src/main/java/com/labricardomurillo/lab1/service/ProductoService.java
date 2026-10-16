package com.labricardomurillo.lab1.service;


import com.labricardomurillo.lab1.model.Categoria;
import com.labricardomurillo.lab1.model.Producto;
import com.labricardomurillo.lab1.repository.CategoriaRepository;
import com.labricardomurillo.lab1.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        log.info("Buscando el producto con identificador: ", id);
        return productoRepository.findById(id);
    }

    public Producto create(Producto producto) {
        if(producto.getCategoria() == null
            || producto.getCategoria().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto debe tener una categoría.");
        }
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Debe ingresar una categoría existente."));

        producto.setId(null);
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    @Transactional
    public Optional<Producto> update(Long id, Producto datos) {
        return productoRepository.findById(id).map(producto -> {
            if (datos.getCategoria() == null
            || datos.getCategoria().getId() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Debe indicar el identificador de la categoría."
                );
            }

            Categoria categoria = categoriaRepository.findById(datos.getCategoria().getId())
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Debe ingresar una categoría existente."
                                    ));

            producto.setNombre(datos.getNombre());
            producto.setDescripcion(datos.getDescripcion());
            producto.setPrecio(datos.getPrecio());
            producto.setCantidad(datos.getCantidad());
            producto.setCategoria(categoria);
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

}
