package com.labricardomurillo.lab1.mappers;

import com.labricardomurillo.lab1.dto.CategoriaDTO;
import com.labricardomurillo.lab1.dto.CategoriaResumenDTO;
import com.labricardomurillo.lab1.dto.ProductoResumenDTO;
import com.labricardomurillo.lab1.model.Categoria;
import com.labricardomurillo.lab1.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    CategoriaDTO toDTO(Categoria categoria);

    List<CategoriaDTO> toDTOList(List<Categoria> categorias);

    @Mapping(target = "productos", ignore = true)
    Categoria toEntity(CategoriaDTO categoriaDTO);

}
