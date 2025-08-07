package com.Ecommerce.ApliServi.App.Usuario.Dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDto {
    private int idUsuario;
    private String email;
    private String password;
    private String username;
    private int idPersona;
    private List<String> roles;

}
