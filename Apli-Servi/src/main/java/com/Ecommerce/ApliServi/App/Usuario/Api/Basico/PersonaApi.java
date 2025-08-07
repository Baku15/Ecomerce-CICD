package com.Ecommerce.ApliServi.App.Usuario.Api.Basico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Usuario.Dto.PersonaDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Usuario.Service.Service.PersonaService;

@RestController
@RequestMapping("/api/persona")
public class PersonaApi {
    private final PersonaService personaService;

    @Autowired
    public PersonaApi(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Respuesta> createPersona(@RequestBody PersonaDto personaDto) {
        try {
            PersonaDto createdPersona = personaService.createPersona(personaDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdPersona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllPersonas() {
        try {
            List<PersonaDto> personas = personaService.getAllPersonas();
            return ResponseEntity.ok(new Respuesta("SUCCESS", personas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Respuesta> updatePersona(@PathVariable int id, @RequestBody PersonaDto personaDto) {
        try {
            PersonaDto updatedPersona = personaService.updatePersona(id, personaDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedPersona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Respuesta> deletePersona(@PathVariable int id) {
        try {
            personaService.deletePersona(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", "Persona eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/allPages")
    public ResponseEntity<Respuesta> getAllPersonas(Pageable pageable) {
        try {
            Page<PersonaDto> personasPage = personaService.getAllPersonasPageable(pageable);
            return ResponseEntity.ok()
                    .header("X-Total-Elements", String.valueOf(personasPage.getTotalElements()))
                    .header("X-Total-Pages", String.valueOf(personasPage.getTotalPages()))
                    .body(new Respuesta("SUCCESS", personasPage.getContent()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}