package com.Ecommerce.ApliServi.App.Producto.Api;
import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.AutoDto;
import com.Ecommerce.ApliServi.App.Producto.Entity.PageableQuery;
import com.Ecommerce.ApliServi.App.Producto.Entity.Respuesta;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.AutoInterface;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/auto")
public class AutoController {

    private final AutoService autoService;

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping("/all-paginated")
    public ResponseEntity<Respuesta> getAllAutos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int elementosPorPagina,
            @RequestParam(defaultValue = "id") String ordenadoPor,
            @RequestParam(defaultValue = "ASC") String enOrden) {

        Pageable pageable = PageRequest.of(pagina, elementosPorPagina, Sort.by(Sort.Direction.fromString(enOrden), ordenadoPor));
        try {
            Page<AutoDto> autos = autoService.getAllAutos(pageable);
            return ResponseEntity.ok(new Respuesta("SUCCESS", autos.getContent()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al obtener todos los autos paginados: " + e.getMessage()));
        }
    }


    @PostMapping("/crear")
    public ResponseEntity<Respuesta> createAuto(@RequestBody AutoDto autoDto) {
        try {
            AutoDto createdAuto = autoService.createAuto(autoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Respuesta("SUCCESS", createdAuto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al crear el auto: " + e.getMessage()));
        }
    }
}