package com.Ecommerce.ApliServi.App.Usuario.Service.Interface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Ecommerce.ApliServi.App.Usuario.Dto.PersonaDto;

public interface PersonaInterface {
    PersonaDto createPersona(PersonaDto personaDto);

    PersonaDto getPersonaById(int id);

    List<PersonaDto> getAllPersonas();

    PersonaDto updatePersona(int id, PersonaDto personaDto);

    void deletePersona(int id);

    Page<PersonaDto> getAllPersonasPageable(Pageable pageable);
}
