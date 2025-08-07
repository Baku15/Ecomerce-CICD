package com.Ecommerce.ApliServi.App.Usuario.Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Dto.PersonaDto;
import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.PersonaRepository;
import com.Ecommerce.ApliServi.App.Usuario.Service.Interface.PersonaInterface;

@Service
public class PersonaService implements PersonaInterface {
    @Autowired
    private PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Método para crear una nueva persona a partir de un DTO
    public PersonaDto createPersona(PersonaDto personaDto) {
        PersonaEntity personaEntity = mapToEntity(personaDto);
        return mapToDto(personaRepository.save(personaEntity));
    }

    // Método para obtener una persona por su ID
    public PersonaDto getPersonaById(int id) {
        PersonaEntity personaEntity = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));
        return mapToDto(personaEntity);
    }

    // Método para obtener todas las personas
    public List<PersonaDto> getAllPersonas() {
        return personaRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Método para obtener todas paginacion las personas
    public Page<PersonaDto> getAllPersonasPageable(Pageable pageable) {
        Page<PersonaEntity> personasPage = personaRepository.findAll(pageable);
        return personasPage.map(this::mapToDto);
    }

    // Método para actualizar una persona existente
    public PersonaDto updatePersona(int id, PersonaDto personaDto) {
        PersonaEntity existingPersona = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));
        PersonaEntity updatedPersona = mapToEntity(personaDto);
        updatedPersona.setIdPersona(existingPersona.getIdPersona());
        return mapToDto(personaRepository.save(updatedPersona));
    }

    // Método para eliminar una persona por su ID
    public void deletePersona(int id) {
        personaRepository.deleteById(id);
    }

    // Método para mapear una entidad Persona a un DTO PersonaDto
    private PersonaDto mapToDto(PersonaEntity personaEntity) {
        PersonaDto personaDto = new PersonaDto();
        personaDto.setIdPersona(personaEntity.getIdPersona());
        personaDto.setNombre(personaEntity.getNombre());
        personaDto.setApellido(personaEntity.getApellido());
        personaDto.setCarnet(personaEntity.getCarnet());
        personaDto.setTelefono(personaEntity.getTelefono());
        return personaDto;
    }

    // Método para mapear un DTO PersonaDto a una entidad PersonaEntity
    private PersonaEntity mapToEntity(PersonaDto personaDto) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setNombre(personaDto.getNombre());
        personaEntity.setApellido(personaDto.getApellido());
        personaEntity.setCarnet(personaDto.getCarnet());
        personaEntity.setTelefono(personaDto.getTelefono());
        return personaEntity;
    }
}
