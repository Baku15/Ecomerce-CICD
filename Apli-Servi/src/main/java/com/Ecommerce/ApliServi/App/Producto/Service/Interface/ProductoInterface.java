package com.Ecommerce.ApliServi.App.Producto.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.ProductoDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Crear.CrearProductoDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.PageableQuery;
import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoInterface {
    ProductoDto createProductoDto(CrearProductoDto productoAuxDto, int userId);
    ProductoDto getProductoById(int id);
    List<ProductoDto> getAllProducto();
    Page<ProductoDto> getProductosByName(String nombre, Pageable pageable);
    ProductoDto updateProducto(int id, CrearProductoDto productoAuxDto);
    void deleteProducto(int id);
    Page<ProductoDto> getAllProductosPaginados(Pageable pageable);
    Page<ProductoDto> getProductosByNombrePaginados(String nombre, Pageable pageable);
    Page<ProductoDto> getProductosByUsuarioPaginados(int userId, Pageable pageable);
}