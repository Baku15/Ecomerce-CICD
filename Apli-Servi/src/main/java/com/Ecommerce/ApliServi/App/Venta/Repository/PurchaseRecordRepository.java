package com.Ecommerce.ApliServi.App.Venta.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Venta.Entity.PurchaseRecordEntity;

import java.util.Optional;

@Repository
public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecordEntity, Integer> {
    Optional<PurchaseRecordEntity> findByUser_IdUsuario(int idUsuario);
}
