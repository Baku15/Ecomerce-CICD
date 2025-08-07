package com.Ecommerce.ApliServi.App.Usuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Usuario.Entity.AutorizacionEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.EAutorizacion;

@Repository
public interface AutorizacionRepository extends JpaRepository<AutorizacionEntity, Integer> {
    AutorizacionEntity findByPermiso(EAutorizacion autorizacion);
}
