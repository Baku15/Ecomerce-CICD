package com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuxProDto1 {
    private String nombre;
    private String descripcion;
    private String imageUrl;
    private Integer stock;
    private BigDecimal precio;
    private List<Integer> categorias;
    private List<Integer> usuarios;
}
