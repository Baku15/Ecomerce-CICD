package com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class BusPersonaDto {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String carnet;
    private String telefono;
    private List<UserAuxDto> userAuxDtoList;
}
