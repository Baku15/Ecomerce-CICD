package com.Ecommerce.ApliServi.App.Usuario.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.BusRolDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.RolAuxDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Usuario.Service.Interface.BusquedaRolInterface;

import java.util.List;

@RestController
@RequestMapping("/api/BusquedasR")
public class BusquedaRolApi {
    @Autowired
    BusquedaRolInterface busquedaRolInterface;

    @GetMapping("/buscarRol/{nombreRol}")
    public ResponseEntity<Respuesta> getUsuariosConRol(@PathVariable String nombreRol) {
        try {
            List<BusRolDto> usuariosConRol = busquedaRolInterface.asignarRolAUsuarios(nombreRol);
            if (usuariosConRol.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Respuesta("ERROR", "No se encontraron usuarios con el rol: " + nombreRol));
            }
            return ResponseEntity.ok(new Respuesta("SUCCESS", usuariosConRol));
        } catch (RolNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Rol no encontrado: " + nombreRol));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al procesar la solicitud"));
        }
    }

    public class RolNotFoundException extends RuntimeException {
        public RolNotFoundException(String mensaje) {
            super(mensaje);
        }
    }

    @PutMapping("/{userId}/roles")
    public ResponseEntity<Respuesta> updateUserRoles(@PathVariable int userId, @RequestBody List<String> newRoles) {
        try {
            RolAuxDto updatedUser = busquedaRolInterface.updateUserRoles(userId, newRoles);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}
