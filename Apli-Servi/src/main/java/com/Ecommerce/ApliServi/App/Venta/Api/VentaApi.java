package com.Ecommerce.ApliServi.App.Venta.Api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Venta.Dto.ventaDto;
import com.Ecommerce.ApliServi.App.Venta.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Venta.Service.Service.ServiciosVenta;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/venta")
public class VentaApi {
    private final ServiciosVenta serviciosVenta;

    @Autowired
    public VentaApi(ServiciosVenta serviciosVenta) {
        this.serviciosVenta = serviciosVenta;
    }

    @PostMapping("/crear")
    public ResponseEntity<Respuesta> createVenta(@RequestBody ventaDto ventaDto) {
        try {
            ventaDto createdVenta = serviciosVenta.createVenta(ventaDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdVenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Respuesta> getVentaById(@PathVariable int id) {
        try {
            ventaDto ventaDto = serviciosVenta.getVentaById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventaDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Venta no encontrada por id: " + id));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllVentas() {
        try {
            List<ventaDto> ventas = serviciosVenta.getAllVentas();
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<Respuesta> getVentasByUserId(@PathVariable int userId) {
        try {
            List<ventaDto> ventas = serviciosVenta.getVentasByUserId(userId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}/daterange")
    public ResponseEntity<Respuesta> getVentasByUserIdAndDateRange(
            @PathVariable int userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<ventaDto> ventas = serviciosVenta.getVentasByUserIdAndDateRange(userId, startDate.toLocalDate(), endDate.toLocalDate());
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}/product/{productId}")
    public ResponseEntity<Respuesta> getVentasByUserIdAndProductId(@PathVariable int userId, @PathVariable int productId) {
        try {
            List<ventaDto> ventas = serviciosVenta.getVentasByUserIdAndProductId(userId, productId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}/categoria/{categoryId}")
    public ResponseEntity<Respuesta> getVentasByUserIdAndCategoryId(@PathVariable int userId, @PathVariable int categoryId) {
        try {
            List<ventaDto> ventas = serviciosVenta.getVentasByUserIdAndCategoryId(userId, categoryId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}/paginated")
    public ResponseEntity<Respuesta> getSalesPaginated(
            @PathVariable int userId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sortColumn,
            @RequestParam(required = false) String sortOrder) {
        try {
            Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<ventaDto> ventas = serviciosVenta.getSalesPaginated(userId, pageable);
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{userId}/search")
    public ResponseEntity<Respuesta> searchVentasByUserId(
            @PathVariable int userId,
            @RequestParam String nombreProducto,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sortColumn,
            @RequestParam(required = false) String sortOrder) {
        try {
            Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<ventaDto> ventas = serviciosVenta.searchVentasByUserId(userId, nombreProducto, pageable);
            return ResponseEntity.ok(new Respuesta("SUCCESS", ventas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}