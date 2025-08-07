package com.Ecommerce.ApliServi.App.Producto.Dto;

import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.AuxProDto2;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CategoriaDtoPro {
    private String nombre;
    private List<AuxProDto2> proDto2List;
}
