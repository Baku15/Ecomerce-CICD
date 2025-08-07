package com.Ecommerce.ApliServi.App.Producto.Api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Producto.Dto.auxp.ProductoDtoA;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.AuxInterface;

@RestController
@RequestMapping("/api/productoAux")
public class Auxapi {
    private final AuxInterface productoInterface;

    public Auxapi(AuxInterface productoInterface) {
        this.productoInterface = productoInterface;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Respuesta> getProductoById(@PathVariable int id, @RequestBody ProductoDtoA productoDtoA) {
        try {
            productoDtoA = productoInterface.createaD(id, productoDtoA);
            return ResponseEntity.ok(new Respuesta("SUCCESS", productoDtoA));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Producto no encontrado con ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al obtener el producto: " + e.getMessage()));
        }
    }
}
