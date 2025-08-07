package com.Ecommerce.ApliServi.App.Producto.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.ProductoDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Crear.CrearProductoDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.PageableQuery;
import com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Producto.Service.Service.ProductoService;


@RestController
@RequestMapping("/api/producto")
public class ProductoApi {
    private final ProductoService productoService;

    @Autowired
    public ProductoApi(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Crear producto (rol: EMPLEADO)
    @PostMapping("/crear")
    public ResponseEntity<Respuesta> createProductoDto(@ModelAttribute CrearProductoDto productoAuxDto, @RequestParam int userId) {
        try {
            ProductoDto productoDto = productoService.createProductoDto(productoAuxDto, userId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", productoDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    // Obtener producto por ID (rol: Comprador o EMPLEADO)
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable int id) {
        try {
            ProductoDto productoDto = productoService.getProductoById(id);
            return ResponseEntity.ok(productoDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ERROR: Producto no encontrado con ID: " + id);
        }
    }

    // Obtener todos los productos (rol: Comprador o EMPLEADO)
    @GetMapping("/all")
    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        try {
            List<ProductoDto> productos = productoService.getAllProducto();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener todos los productos paginados (rol: Comprador o EMPLEADO)
    @GetMapping("/all-paginated")
    public ResponseEntity<Page<ProductoDto>> getAllProductosPaginados(Pageable pageable) {
        try {
            Page<ProductoDto> productos = productoService.getAllProductosPaginados(pageable);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Método para buscar productos por nombre (Rol: Comprador)
    @GetMapping("/search/{nombre}")
    public ResponseEntity<?> getProductosByName(@PathVariable String nombre, Pageable pageable) {
        try {
            Page<ProductoDto> productos = productoService.getProductosByNombrePaginados(nombre, pageable);
            if (productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos con el nombre: " + nombre);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR: No se pudo realizar la búsqueda de productos por nombre: " + e.getMessage());
        }
    }

    // Método para buscar productos por nombre y usuario (Rol: Empleado)
    @GetMapping("/search/{nombre}/usuario/{userId}")
    public ResponseEntity<?> getProductosByNameAndUserId(@PathVariable String nombre, @PathVariable int userId, Pageable pageable) {
        try {
            Page<ProductoDto> productos = productoService.getProductosByNombreAndUsuarioPaginados(nombre, userId, pageable);
            if (productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos con el nombre: " + nombre);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR: No se pudo realizar la búsqueda de productos por nombre: " + e.getMessage());
        }
    }

    // Método para obtener productos por usuario (Rol: Empleado)
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<Page<ProductoDto>> getProductosByUsuarioPaginados(@PathVariable int userId, Pageable pageable) {
        try {
            Page<ProductoDto> productos = productoService.getProductosByUsuarioPaginados(userId, pageable);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar producto (rol: EMPLEADO)
    @PutMapping("/editar/{id}")
    public ResponseEntity<Respuesta> updateProducto(@PathVariable int id,
                                                    @ModelAttribute CrearProductoDto productoAuxDto) {
        try {
            ProductoDto updatedProducto = productoService.updateProducto(id, productoAuxDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedProducto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al actualizar el producto: " + e.getMessage()));
        }
    }

    // Eliminar producto (rol: EMPLEADO)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Respuesta> deleteProducto(@PathVariable int id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", "Producto eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", "Error al eliminar el producto: " + e.getMessage()));
        }
    }

    // Obtener productos con bajo stock por usuario (rol: EMPLEADO)
    @GetMapping("/low-stock/{userId}")
    public ResponseEntity<List<ProductoDto>> getLowStockProductsByUserId(@PathVariable int userId) {
        try {
            List<ProductoDto> productos = productoService.getLowStockProductsByUserId(userId);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}