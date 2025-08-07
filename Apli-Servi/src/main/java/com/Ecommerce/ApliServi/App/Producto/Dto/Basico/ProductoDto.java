package com.Ecommerce.ApliServi.App.Producto.Dto.Basico;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductoDto {
    private int id;
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private Integer stock;
    private BigDecimal precio;
    private String marca;
    private String productName;
    private List<String> categorias;
    private List<Integer> usuarios;
    private List<Integer> descuento;
}
