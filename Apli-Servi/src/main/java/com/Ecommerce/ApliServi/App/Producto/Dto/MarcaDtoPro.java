package com.Ecommerce.ApliServi.App.Producto.Dto;

import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.AuxProDto1;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MarcaDtoPro {
    private String nombre;
    private List<AuxProDto1> proDto1;
}
