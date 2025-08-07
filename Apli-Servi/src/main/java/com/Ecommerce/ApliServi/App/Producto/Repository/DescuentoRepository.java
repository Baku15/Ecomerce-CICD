package com.Ecommerce.ApliServi.App.Producto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Producto.Entity.DescuentoEntity;

@Repository
public interface DescuentoRepository extends JpaRepository<DescuentoEntity, Integer> {

}
