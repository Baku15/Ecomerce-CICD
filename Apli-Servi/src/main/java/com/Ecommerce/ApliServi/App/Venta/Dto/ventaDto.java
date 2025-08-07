package com.Ecommerce.ApliServi.App.Venta.Dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ventaDto {
    private int id;
    private int cantidad;
    private Date fechaVenta;
    private BigDecimal precio;
    private int productoId;
    private String productoNombre; // Nuevo campo para el nombre del producto
    private int usuarioId;
}
