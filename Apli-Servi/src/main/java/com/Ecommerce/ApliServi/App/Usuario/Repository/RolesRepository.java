package com.Ecommerce.ApliServi.App.Usuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Usuario.Entity.RolesEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.ERole;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
    Optional<RolesEntity> findByRoles(ERole role);
}
