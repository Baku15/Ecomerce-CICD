package com.Ecommerce.ApliServi.App.Usuario.Service.Interface;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.BusPersonaDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.CreateDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.PageableQuery;

import java.util.List;

public interface BusquedaPersonaInterface {
    List<BusPersonaDto> getPersonasS(PageableQuery pageableQuery);

    List<BusPersonaDto> getPersonasS();

    BusPersonaDto buscarPorCarnet(String carnet);

    List<BusPersonaDto> buscarPorNombre(String nombre);

    CreateDto createPersonaUser(CreateDto user);

    void deletePersonaUser(int userId);

    CreateDto updatePersonaUser(int userId, CreateDto user);
}
