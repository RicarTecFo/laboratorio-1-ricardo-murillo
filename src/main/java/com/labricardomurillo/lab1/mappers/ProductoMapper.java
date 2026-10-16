package com.labricardomurillo.lab1.mappers;

import com.labricardomurillo.lab1.dto.ProductoDTO;
import com.labricardomurillo.lab1.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses =  CategoriaMapper.class)
public interface ProductoMapper {

    ProductoDTO toDto(Producto producto);

    List<ProductoDTO> toDto(List<Producto> productos);

    Producto toEntity(ProductoDTO productoDTO);

}
