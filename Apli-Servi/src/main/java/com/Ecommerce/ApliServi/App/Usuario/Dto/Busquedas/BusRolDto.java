package com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class BusRolDto {
   private int id;
   private String rol;
   private PersonaAuxDto persona;
}
