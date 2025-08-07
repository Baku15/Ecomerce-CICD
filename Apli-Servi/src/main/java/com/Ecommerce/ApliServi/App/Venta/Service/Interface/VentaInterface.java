package com.Ecommerce.ApliServi.App.Venta.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Venta.Dto.ventaDto;

public interface VentaInterface {
    ventaDto createVenta(ventaDto ventaDto);

    ventaDto getVentaById(int id);

    List<ventaDto> getAllVentas();


}
