package com.Ecommerce.ApliServi.App.Producto.Api;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.CategoriaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.CategoriaInterface;

@RestController
@RequestMapping("/api")
public class CategoriaApi {
    private final CategoriaInterface categoriaService;

    @Autowired
    public CategoriaApi(CategoriaInterface categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/Categoria")
    public ResponseEntity<Respuesta> createCategoria(@RequestBody CategoriaDto categoriaDto) {
        try {
            CategoriaDto createdCategoria = categoriaService.createCategoria(categoriaDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdCategoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/Categoria/{id}")
    public ResponseEntity<Respuesta> getCategoriaById(@PathVariable int id) {
        try {
            CategoriaDto categoria = categoriaService.getCategoriaById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", categoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }



    @GetMapping("/Categorias")
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        try {
            List<CategoriaDto> categorias = categoriaService.getAllCategorias();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/Categoria/{id}")
    public ResponseEntity<Respuesta> updateCategoria(@PathVariable int id, @RequestBody CategoriaDto categoriaDto) {
        try {
            CategoriaDto updatedCategoria = categoriaService.updateCategoria(id, categoriaDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedCategoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/Categoria/{id}")
    public ResponseEntity<Respuesta> deleteCategoria(@PathVariable int id) {
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}
