package com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RolAuxDto {
    private int idUsuario;
    private List<String> roles;
}