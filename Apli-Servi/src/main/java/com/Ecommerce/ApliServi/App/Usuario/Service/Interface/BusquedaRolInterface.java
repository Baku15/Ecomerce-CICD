package com.Ecommerce.ApliServi.App.Usuario.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.BusRolDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.RolAuxDto;

public interface BusquedaRolInterface {
    List<BusRolDto> asignarRolAUsuarios(String nombreRol);

    RolAuxDto updateUserRoles(int userId, List<String> newRoles);
}
