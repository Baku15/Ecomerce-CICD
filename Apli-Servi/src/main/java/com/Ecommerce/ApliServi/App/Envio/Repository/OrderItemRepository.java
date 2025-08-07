package com.Ecommerce.ApliServi.App.Envio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Envio.Entity.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

}
