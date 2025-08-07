package com.Ecommerce.ApliServi.App.Usuario_Complemento.Repository;

import org.springframework.stereotype.Repository;

import com.Ecommerce.ApliServi.App.Usuario_Complemento.Entity.AddressEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

}
