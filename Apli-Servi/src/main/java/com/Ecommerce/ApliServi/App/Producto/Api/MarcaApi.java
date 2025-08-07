package com.Ecommerce.ApliServi.App.Producto.Api;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.MarcaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Crear.CrearMarcaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.MarcaDtoBas;
import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.MarcaInterface;

@RestController
@RequestMapping("/api/marca")
public class MarcaApi {
    private final MarcaInterface marcaInterface;

    @Autowired
    public MarcaApi(MarcaInterface marcaInterface) {
        this.marcaInterface = marcaInterface;
    }

    @PostMapping("/crear")
    public ResponseEntity<Respuesta> createMarca(@ModelAttribute CrearMarcaDto ma) {
        try {
            MarcaDto createdMarca = marcaInterface.createMarcaDto(ma);

            return ResponseEntity.ok(new Respuesta("SUCCESS", createdMarca));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al crear la marca: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Respuesta> getMarcaById(@PathVariable int id) {
        try {
            MarcaDto marcaDto = marcaInterface.getMarcaById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", marcaDto));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Marca no encontrada con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al obtener la marca: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MarcaDto>> getAllMarcas() {
        try {
            List<MarcaDto> marcas = marcaInterface.getAllMarca();
            return ResponseEntity.ok(marcas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Respuesta> updateMarca(@PathVariable int id, @ModelAttribute CrearMarcaDto ma) {
        try {
            MarcaDto updatedMarca = marcaInterface.updateMarca(id, ma);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedMarca));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Marca no encontrada con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al actualizar la marca: " + e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Respuesta> deleteMarca(@PathVariable int id) {
        try {
            marcaInterface.deleteMarca(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", "Marca eliminada correctamente"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Marca no encontrada con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al eliminar la marca: " + e.getMessage()));
        }
    }

    @GetMapping("/all-basic")
    public ResponseEntity<Respuesta> getAllMarcasBasic() {
        try {
            List<MarcaDtoBas> marcasBasic = marcaInterface.getAllMarcas();
            return ResponseEntity.ok(new Respuesta("SUCCESS", marcasBasic));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al obtener todas las marcas básicas: " + e.getMessage()));
        }
    }
}
