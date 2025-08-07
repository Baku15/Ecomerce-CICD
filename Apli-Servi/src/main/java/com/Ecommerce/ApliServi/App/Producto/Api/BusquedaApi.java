package com.Ecommerce.ApliServi.App.Producto.Api;

import com.Ecommerce.ApliServi.App.Producto.Dto.CategoriaDtoPro;
import com.Ecommerce.ApliServi.App.Producto.Dto.MarcaDtoPro;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.BusquedaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class BusquedaApi {
    @Autowired
    private BusquedaInterface busquedaService;

    @GetMapping("/categorias")
    public ResponseEntity<?> getCategorias(@RequestParam("nombreCategoria") String nombreCategoria) {
        try {
            List<CategoriaDtoPro> categorias = busquedaService.gCategoriaDtoPro(nombreCategoria);
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las categorías: " + e.getMessage());
        }
    }

    @GetMapping("/marcas")
    public ResponseEntity<?> getMarcas(@RequestParam("nombreMarca") String nombreMarca) {
        try {
            List<MarcaDtoPro> marcas = busquedaService.gMarcaDtoPro(nombreMarca);
            return ResponseEntity.ok(marcas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las marcas: " + e.getMessage());
        }
    }

    @GetMapping("/marcas/paginado")
    public ResponseEntity<?> getMarcasPaginadas(@RequestParam("nombreMarca") String nombreMarca, Pageable pageable) {
        try {
            Page<MarcaDtoPro> marcas = busquedaService.gMarcaDtoPro(nombreMarca, pageable);
            return ResponseEntity.ok(marcas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las marcas paginadas: " + e.getMessage());
        }
    }

    @GetMapping("/categorias/paginado")
    public ResponseEntity<?> getCategoriasPaginadas(@RequestParam("nombreCategoria") String nombreCategoria, Pageable pageable) {
        try {
            Page<CategoriaDtoPro> categorias = busquedaService.gCategoriaDtoPro(nombreCategoria, pageable);
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las categorías paginadas: " + e.getMessage());
        }
    }
}
