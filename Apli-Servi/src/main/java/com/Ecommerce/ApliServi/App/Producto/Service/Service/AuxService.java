package com.Ecommerce.ApliServi.App.Producto.Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Producto.Dto.auxp.ProductoDtoA;
import com.Ecommerce.ApliServi.App.Producto.Entity.CategoriaEntity;
import com.Ecommerce.ApliServi.App.Producto.Entity.DescuentoEntity;
import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.CategoriaRepository;
import com.Ecommerce.ApliServi.App.Producto.Repository.DescuentoRepository;
import com.Ecommerce.ApliServi.App.Producto.Repository.MarcaRepository;
import com.Ecommerce.ApliServi.App.Producto.Repository.ProductoRepository;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.AuxInterface;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;

@Service
public class AuxService implements AuxInterface {
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

    @Override
    public ProductoDtoA createaD(int id, ProductoDtoA p) {
        try {
            // Crear una nueva entidad de producto
            ProductoEntity productoEntity = new ProductoEntity();

            // Asignar el ID del producto
            productoEntity.setId(id);

            // Mapear los campos de ProductoDtoA a ProductoEntity
            // Mapear las listas de categoría, usuario y descuento a entidades si es
            // necesario
            if (p.getCategoria() != null) {
                List<CategoriaEntity> categorias = p.getCategoria().stream()
                        .map(catId -> categoriaRepository.findById(catId)
                                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + catId)))
                        .collect(Collectors.toList());
                productoEntity.setCategorias(categorias);
            }
            if (p.getUsuario() != null) {
                List<UserEntity> usuarios = p.getUsuario().stream()
                        .map(userId -> usuarioRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId)))
                        .collect(Collectors.toList());
                productoEntity.setUsuarios(usuarios);
            }
            if (p.getDescuento() != null) {
                List<DescuentoEntity> descuentos = p.getDescuento().stream()
                        .map(descuentoId -> descuentoRepository.findById(descuentoId)
                                .orElseThrow(
                                        () -> new RuntimeException("Descuento no encontrado con ID: " + descuentoId)))
                        .collect(Collectors.toList());
                productoEntity.setDiscounts(descuentos);
            }
            // Otros mapeos de campos de ProductoDtoA a ProductoEntity

            // Guardar la entidad de producto en la base de datos
            productoEntity = productoRepository.save(productoEntity);

            // Mapear la entidad de producto creada a ProductoDtoA y devolverla
            return mapToDtoA(productoEntity);
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante el proceso
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }

    private ProductoDtoA mapToDtoA(ProductoEntity entity) {
        ProductoDtoA dto = new ProductoDtoA();
        // Mapear las listas de categoría, usuario y descuento a listas de IDs
        if (entity.getCategorias() != null) {
            dto.setCategoria(entity.getCategorias().stream()
                    .map(CategoriaEntity::getId)
                    .collect(Collectors.toList()));
        }
        if (entity.getUsuarios() != null) {
            dto.setUsuario(entity.getUsuarios().stream()
                    .map(UserEntity::getIdUsuario)
                    .collect(Collectors.toList()));
        }
        if (entity.getDiscounts() != null) {
            dto.setDescuento(entity.getDiscounts().stream()
                    .map(DescuentoEntity::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
