package com.Ecommerce.ApliServi.App.Producto.Dto.Crear;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrearProductoDto {
    private int id;
    private String nombre;
    private String descripcion;
    private MultipartFile imageUrl;
    private Integer stock;
    private BigDecimal precio;
    private int marca;
    private List<Integer> categorias;
    private List<Integer> usuarios;
    private List<Integer> descuento;
}
