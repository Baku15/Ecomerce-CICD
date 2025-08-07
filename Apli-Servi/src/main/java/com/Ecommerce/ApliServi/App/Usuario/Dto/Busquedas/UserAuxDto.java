package com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserAuxDto {
    private int idUsuario;
    private String email;
    private String password;
    private String username;
    private List<String> roles;
}
