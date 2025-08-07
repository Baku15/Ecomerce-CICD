package com.Ecommerce.ApliServi.App.Producto.Repository;

import com.Ecommerce.ApliServi.App.Producto.Entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
}