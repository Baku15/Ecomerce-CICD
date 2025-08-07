package com.Ecommerce.ApliServi.App.Producto.Dto.Basico;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CategoriaDto {
    private int idCategoria;
    private String categoria;
    private String descripcion;
}
