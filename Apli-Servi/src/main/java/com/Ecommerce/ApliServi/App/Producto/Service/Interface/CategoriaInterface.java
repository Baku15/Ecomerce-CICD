package com.Ecommerce.ApliServi.App.Producto.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.CategoriaDto;

public interface CategoriaInterface {
    CategoriaDto createCategoria(CategoriaDto categoriaDto);

    CategoriaDto getCategoriaById(int id);

    List<CategoriaDto> getAllCategorias();

    CategoriaDto updateCategoria(int id, CategoriaDto categoriaDto);

    void deleteCategoria(int id);
}
