package com.Ecommerce.ApliServi.App.Producto.Service.Interface;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.AutoDto;
import com.Ecommerce.ApliServi.App.Producto.Entity.PageableQuery;

import java.util.List;

public interface AutoInterface {

    List<AutoDto> getAllAutos(PageableQuery pageableQuery);

    AutoDto createAuto(AutoDto autoDto); // Nuevo método para crear un auto


}
