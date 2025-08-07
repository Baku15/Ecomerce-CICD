package com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonaAuxDto {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String carnet;
    private String telefono;
    private String email;
    private String password;
    private String username;
}
