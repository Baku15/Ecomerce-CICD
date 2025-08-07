package com.Ecommerce.ApliServi.App.Usuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;

import java.util.List;

//interface entre la base de datos con backend
@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
    PersonaEntity findByCarnet(String carnet);

    List<PersonaEntity> findByNombre(String nombre);
}
