package com.Ecommerce.ApliServi.App.Producto.Dto.Basico;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MarcaDto {
    private int id;
    private String nombre;
    private String descripcion;
    private String imagen;
    List<ProductoDto> ProductoDto;
}
