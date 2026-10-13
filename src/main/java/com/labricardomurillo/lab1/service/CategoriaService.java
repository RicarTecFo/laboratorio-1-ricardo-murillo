package com.labricardomurillo.lab1.service;

import com.labricardomurillo.lab1.mappers.CategoriaMapper;
import com.labricardomurillo.lab1.model.Categoria;
import com.labricardomurillo.lab1.repository.CategoriaRepository;
import com.labricardomurillo.lab1.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository,  CategoriaMapper categoriaMapper, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        log.info("Buscando duenno con id {}:", id);
        return categoriaRepository.findById(id);
    }

    public Categoria create(Categoria categoria) {
        categoria.setId(null);
        categoria.getProductos().clear();
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> update(Long id, Categoria datos) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(datos.getNombre());
            categoria.setDescripcion(datos.getDescripcion());
            return categoriaRepository.save(categoria);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if(!categoriaRepository.existsById(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<Categoria> agregarProducto(Long categoriaId, Long productoId) {
        return  categoriaRepository.findById(categoriaId).flatMap(categoria ->
                productoRepository.findById(productoId).map(producto -> {
                    producto.setCategoria(categoria);
                    return categoria;
                }));
    }

    @Transactional
    public Optional<Categoria> quitarProducto(Long categoriaId, Long productoId) {
        return categoriaRepository.findById(categoriaId).map(categoria -> {
            categoria.setProductos(null);
            return categoriaRepository.save(categoria);
        });
    }

}
