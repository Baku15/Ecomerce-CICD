package com.Ecommerce.ApliServi.App.Producto.Repository;

import com.Ecommerce.ApliServi.App.Producto.Entity.UserProduct;
import com.Ecommerce.ApliServi.App.Producto.Entity.UserProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, UserProductId> {
}