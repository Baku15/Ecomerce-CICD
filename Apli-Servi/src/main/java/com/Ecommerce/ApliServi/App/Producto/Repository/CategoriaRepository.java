package com.Ecommerce.ApliServi.App.Producto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Producto.Entity.CategoriaEntity;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

    Optional<CategoriaEntity> findByNombre(String nombre);

}