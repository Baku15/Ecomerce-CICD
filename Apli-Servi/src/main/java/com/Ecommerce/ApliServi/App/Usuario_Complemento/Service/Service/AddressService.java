package com.Ecommerce.ApliServi.App.Usuario_Complemento.Service.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.PersonaRepository;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.AddressDto;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Entity.AddressEntity;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Repository.AddressRepository;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Service.Interface.AddressInterface;

@Service
public class AddressService implements AddressInterface {
    private AddressRepository addressRepository;
    private PersonaRepository personaRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, PersonaRepository personaRepository) {
        this.addressRepository = addressRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public AddressDto save(AddressDto dto) {
        AddressEntity entity = convertToEntity(dto);
        AddressEntity savedEntity = addressRepository.save(entity);
        return convertToDto(savedEntity);
    }

    @Override
    public AddressDto findById(int id) {
        AddressEntity entity = addressRepository.findById(id).orElse(null);
        return convertToDto(entity);
    }

    @Override
    public List<AddressDto> findAll() {
        List<AddressEntity> entities = addressRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public AddressDto update(int id, AddressDto dto) {
        // Verifica si la dirección con el ID proporcionado existe en la base de datos
        AddressEntity existingEntity = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + id));

        // Actualiza los campos de la entidad existente con los valores del DTO
        existingEntity.setAddressId(dto.getAddressId()); // Asegura que el ID sea el correcto
        existingEntity.setAddress(dto.getAddress());
        existingEntity.setCity(dto.getCity());
        existingEntity.setPostalCode(dto.getPostalCode());

        // Si es necesario, actualiza la persona asociada a la dirección
        if (dto.getPeopleId() != 0) {
            PersonaEntity personaEntity = personaRepository.findById(dto.getPeopleId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + dto.getPeopleId()));
            existingEntity.setPeople(personaEntity);
        } else {
            existingEntity.setPeople(null); // Si no se proporciona un ID de persona, establece como nulo
        }

        // Guarda la entidad actualizada en la base de datos
        AddressEntity updatedEntity = addressRepository.save(existingEntity);

        // Convierte y devuelve la entidad actualizada como DTO
        return convertToDto(updatedEntity);
    }

    @Override
    public void deleteById(int id) {
        addressRepository.deleteById(id);
    }

    public AddressEntity convertToEntity(AddressDto dto) {
        AddressEntity entity = new AddressEntity();
        entity.setAddressId(dto.getAddressId());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setPostalCode(dto.getPostalCode());
        if (dto.getPeopleId() != 0) {
            PersonaEntity personaEntity = personaRepository.findById(dto.getPeopleId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + dto.getPeopleId()));
            entity.setPeople(personaEntity);
        }
        return entity;
    }

    public AddressDto convertToDto(AddressEntity entity) {
        AddressDto dto = new AddressDto();
        dto.setAddressId(entity.getAddressId());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setPostalCode(entity.getPostalCode());
        if (entity.getPeople() != null) {
            dto.setPeopleId(entity.getPeople().getIdPersona());
        }
        return dto;
    }

}
