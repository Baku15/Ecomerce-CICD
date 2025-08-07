package com.Ecommerce.ApliServi.App.Venta.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Venta.Entity.VentaEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {
    List<VentaEntity> findByUsuario_IdUsuario(int userId);
    List<VentaEntity> findByUsuario_IdUsuarioAndFechaVentaBetween(int userId, LocalDate startDate, LocalDate endDate);
    List<VentaEntity> findByUsuario_IdUsuarioAndProducto_Id(int userId, int productId);
    List<VentaEntity> findByUsuario_IdUsuarioAndProducto_Categorias_Id(int userId, int categoryId);
    Page<VentaEntity> findByUsuario_IdUsuario(int userId, Pageable pageable);
    Page<VentaEntity> findByUsuario_IdUsuarioAndProducto_NombreContainingIgnoreCase(int userId, String nombreProducto, Pageable pageable);
}