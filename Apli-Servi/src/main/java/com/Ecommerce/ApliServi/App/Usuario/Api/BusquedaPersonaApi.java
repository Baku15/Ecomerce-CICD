package com.Ecommerce.ApliServi.App.Usuario.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.BusPersonaDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.CreateDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.PageableQuery;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Usuario.Service.Interface.BusquedaPersonaInterface;

import java.util.List;

@RestController
@RequestMapping("/api/BusquedasP")
public class BusquedaPersonaApi {
    @Autowired
    BusquedaPersonaInterface busPersonaService;

    // Endpoint para obtener personas con paginación y ordenación
    @GetMapping("/ListaP")
    public ResponseEntity<Respuesta> getPersonas(PageableQuery pageableQuery) {
        try {
            List<BusPersonaDto> personas = busPersonaService.getPersonasS(pageableQuery);
            return ResponseEntity.ok(new Respuesta("SUCCESS", personas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Endpoint para obtener todas las personas
    @GetMapping("/Lista")
    public ResponseEntity<Respuesta> obtenerPersonas() {
        try {
            List<BusPersonaDto> personas = busPersonaService.getPersonasS();
            return ResponseEntity.ok(new Respuesta("SUCCESS", personas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Endpoint para buscar personas por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Respuesta> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<BusPersonaDto> personas = busPersonaService.buscarPorNombre(nombre);
            return ResponseEntity.ok(new Respuesta("SUCCESS", personas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Endpoint para buscar personas por carnet
    @GetMapping("/carnet/{carnet}")

    public ResponseEntity<Respuesta> buscarPorCarnet(@PathVariable String carnet) {
        try {
            BusPersonaDto persona = busPersonaService.buscarPorCarnet(carnet);
            return ResponseEntity.ok(new Respuesta("SUCCESS", persona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Endpoint para Crear un usuario Completo (persona y user)
    @PostMapping("/createG")
    public ResponseEntity<Respuesta> createPersonaUser(@RequestBody CreateDto userAuxDto) {
        try {
            CreateDto createdPersona = busPersonaService.createPersonaUser(userAuxDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdPersona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Endpoint para Editar un usario Completo (persona y user)
    @PutMapping("/ActualizarG/{id}")
    public ResponseEntity<Respuesta> updatePersonaUser(@PathVariable int id, @RequestBody CreateDto userAuxDto) {
        try {
            CreateDto createdPersona = busPersonaService.updatePersonaUser(id, userAuxDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdPersona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Endpoint para Eliminar un usuario completo (persona y user)
    @DeleteMapping("/eliminarG/{id}")
    public ResponseEntity<Respuesta> deletePersona(@PathVariable int id) {
        try {
            busPersonaService.deletePersonaUser(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", "Usuario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

}