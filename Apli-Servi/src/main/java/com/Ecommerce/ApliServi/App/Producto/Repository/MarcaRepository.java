package com.Ecommerce.ApliServi.App.Producto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Producto.Entity.MarcaEntity;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaEntity, Integer> {

    Optional<MarcaEntity> findByNombre(String nombre);
}
