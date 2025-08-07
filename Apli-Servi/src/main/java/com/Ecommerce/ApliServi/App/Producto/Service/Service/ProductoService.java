package com.Ecommerce.ApliServi.App.Producto.Service.Service;

import java.io.*;

import com.Ecommerce.ApliServi.App.Producto.Entity.*;
import com.Ecommerce.ApliServi.App.Producto.Repository.*;
import io.minio.*;
import io.minio.http.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.ProductoDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Crear.CrearProductoDto;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.ProductoInterface;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.PageableQuery;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService implements ProductoInterface {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    UserRepository usuarioRepository;
    @Autowired
    MarcaRepository marcaRepository;
    @Autowired
    DescuentoRepository descuentoRepository;
    @Autowired
    private MinioClient minioClient;
    @Autowired
    UserProductRepository userProductRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String BUCKET_NAME = "1-producto";


    @Override
    @Transactional
    // Método para crear un producto (rol: EMPLEADO)
    public ProductoDto createProductoDto(CrearProductoDto productoAuxDto, int userId) {
        ProductoEntity productoEntity = mapToEntity(productoAuxDto);
        if (productoEntity.getNombre() == null || productoEntity.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto no puede ser nulo o vacío.");
        }
        productoEntity = productoRepository.save(productoEntity);
        if (productoEntity.getId() == 0) {
            throw new RuntimeException("No se pudo generar el ID del producto");
        }
        UserEntity user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        UserProduct userProduct = new UserProduct(user, productoEntity);
        userProductRepository.save(userProduct);
        return mapToDto(productoEntity);
    }

    @Override
    // Método para buscar productos por nombre (rol: Comprador)
    public Page<ProductoDto> getProductosByName(String nombre, Pageable pageable) {
        Page<ProductoEntity> productos = productoRepository.findByNombreContaining(nombre, pageable);
        return productos.map(this::mapToDto);
    }

    @Override
    // Método para obtener un producto por su ID (rol: Comprador o EMPLEADO)
    public ProductoDto getProductoById(int id) {
        ProductoEntity productoEntity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return mapToDto(productoEntity);
    }

    @Override
    // Método para obtener todos los productos (rol: Comprador o EMPLEADO)
    public List<ProductoDto> getAllProducto() {
        List<ProductoEntity> productos = productoRepository.findAll();
        return productos.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    // Método para obtener todos los productos paginados (rol: Comprador o EMPLEADO)
    public Page<ProductoDto> getAllProductosPaginados(Pageable pageable) {
        Page<ProductoEntity> productos = productoRepository.findAll(pageable);
        return productos.map(this::mapToDto);
    }

    // Método para obtener productos por nombre y paginados (rol: Comprador)
    public Page<ProductoDto> getProductosByNombrePaginados(String nombre, Pageable pageable) {
        Page<ProductoEntity> productos = productoRepository.findByNombreContaining(nombre, pageable);
        return productos.map(this::mapToDto);
    }

    @Override
    // Método para obtener productos por usuario y paginados (rol: EMPLEADO)
    public Page<ProductoDto> getProductosByUsuarioPaginados(int userId, Pageable pageable) {
        Page<ProductoEntity> productos = productoRepository.findByUsuarios_IdUsuario(userId, pageable);
        return productos.map(this::mapToDto);
    }

    @Override
    // Método para actualizar un producto existente (rol: EMPLEADO)
    public ProductoDto updateProducto(int id, CrearProductoDto productoAuxDto) {
        ProductoEntity existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        existingProducto.setNombre(productoAuxDto.getNombre());
        existingProducto.setDescripcion(productoAuxDto.getDescripcion());
        existingProducto.setStock(productoAuxDto.getStock());
        existingProducto.setPrecio(productoAuxDto.getPrecio());
        if (productoAuxDto.getImageUrl() != null) {
            try {
                String nombreImagen = productoAuxDto.getImageUrl().getOriginalFilename();
                InputStream inputStream = productoAuxDto.getImageUrl().getInputStream();
                saveImage(inputStream, nombreImagen);
                deleteImage(existingProducto.getImageUrl());
                String urlimg = getUrlImagen(nombreImagen);
                existingProducto.setImageUrl(urlimg);
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen: " + e.getMessage());
            }
        }
        productoRepository.save(existingProducto);
        return mapToDto(existingProducto);
    }

    @Override
    // Método para eliminar un producto por su ID (rol: EMPLEADO)
    public void deleteProducto(int id) {
        // Eliminar referencias en us_comment
        String deleteCommentsQuery = "DELETE FROM us_comment WHERE product_id = :productId";
        Map<String, Object> params = new HashMap<>();
        params.put("productId", id);
        namedParameterJdbcTemplate.update(deleteCommentsQuery, params);

        // Eliminar referencias en v_sale
        String deleteSalesQuery = "DELETE FROM v_sale WHERE product_id = :productId";
        namedParameterJdbcTemplate.update(deleteSalesQuery, params);

        // Eliminar referencias en shopping_cart
        String deleteShoppingCartQuery = "DELETE FROM shopping_cart WHERE product_id = :productId";
        namedParameterJdbcTemplate.update(deleteShoppingCartQuery, params);

        // Luego eliminar el producto
        ProductoEntity productoEntity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        deleteImage(productoEntity.getImageUrl());
        productoRepository.delete(productoEntity);
    }

    private ProductoDto mapToDto(ProductoEntity entity) {
        ProductoDto dto = new ProductoDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setImageUrl(entity.getImageUrl());
        dto.setStock(entity.getStock());
        dto.setPrecio(entity.getPrecio());
        dto.setMarca(entity.getMarca().getNombre());
        dto.setCategorias(entity.getCategorias().stream().map(CategoriaEntity::getNombre).collect(Collectors.toList()));
        dto.setUsuarios(entity.getUsuarios().stream().map(UserEntity::getIdUsuario).collect(Collectors.toList()));
        dto.setDescuento(entity.getDiscounts().stream().map(DescuentoEntity::getId).collect(Collectors.toList()));
        return dto;
    }

    private ProductoEntity mapToEntity(CrearProductoDto dto) {
        try {
            ProductoEntity entity = new ProductoEntity();
            entity.setNombre(dto.getNombre());
            entity.setDescripcion(dto.getDescripcion());
            entity.setStock(dto.getStock());
            entity.setPrecio(dto.getPrecio());
            if (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty()) {
                String nombreImagen = dto.getImageUrl().getOriginalFilename();
                InputStream inputStream = dto.getImageUrl().getInputStream();
                saveImage(inputStream, nombreImagen);
                String urlImagen = getUrlImagen(nombreImagen);
                entity.setImageUrl(urlImagen);
            } else {
                throw new RuntimeException("La imagen es nula o vacía.");
            }
            if (dto.getMarca() != 0) {
                MarcaEntity marcaEntity = marcaRepository.findById(dto.getMarca())
                        .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + dto.getMarca()));
                entity.setMarca(marcaEntity);
            }
            if (dto.getCategorias() != null && !dto.getCategorias().isEmpty()) {
                List<CategoriaEntity> categorias = dto.getCategorias().stream()
                        .map(categoryId -> categoriaRepository.findById(categoryId)
                                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoryId)))
                        .collect(Collectors.toList());
                entity.setCategorias(categorias);
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }

    private void saveImage(InputStream inputStream, String objectName) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen de la marca: " + e.getMessage());
        }
    }

    private void deleteImage(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la imagen de la marca: " + e.getMessage());
        }
    }

    private String getUrlImagen(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la URL de la imagen: " + e.getMessage());
        }
    }

    // Método para obtener productos con bajo stock por usuario (rol: EMPLEADO)
    public List<ProductoDto> getLowStockProductsByUserId(int userId) {
        return productoRepository.findByStockLessThanAndUsuarios_IdUsuario(10, userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Método para obtener productos por nombre y usuario paginados (rol: EMPLEADO)
    public Page<ProductoDto> getProductosByNombreAndUsuarioPaginados(String nombre, int userId, Pageable pageable) {
        Page<ProductoEntity> productos = productoRepository.findByNombreContainingAndUsuarios_IdUsuario(nombre, userId, pageable);
        return productos.map(this::mapToDto);
    }
}