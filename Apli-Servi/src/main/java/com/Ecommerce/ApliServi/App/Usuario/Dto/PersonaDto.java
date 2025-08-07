package com.Ecommerce.ApliServi.App.Usuario.Dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PersonaDto {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String carnet;
    private String telefono;
}
