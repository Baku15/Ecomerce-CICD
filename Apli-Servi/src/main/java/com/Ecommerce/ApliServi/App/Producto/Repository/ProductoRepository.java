package com.Ecommerce.ApliServi.App.Producto.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
    Page<ProductoEntity> findByUsuarios_IdUsuario(int userId, Pageable pageable);
    Page<ProductoEntity> findByNombreContaining(String nombre, Pageable pageable);
    List<ProductoEntity> findByStockLessThanAndUsuarios_IdUsuario(int stock, int userId);
    Page<ProductoEntity> findByNombreContainingAndUsuarios_IdUsuario(String nombre, int userId, Pageable pageable);

}