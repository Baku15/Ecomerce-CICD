package com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuxProDto2 {
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private Integer stock;
    private BigDecimal precio;
    private int marca;
    private List<Integer> usuarios;
}
