package com.Ecommerce.ApliServi.App.Usuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;

import java.util.List;
import java.util.Optional;

//interface entre la base de datos con backend

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByIdpersona_IdPersona(int idPersona);
}
