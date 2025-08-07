package com.Ecommerce.ApliServi.App.Producto.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.MarcaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Crear.CrearMarcaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.MarcaDtoBas;

public interface MarcaInterface {
    MarcaDto createMarcaDto(CrearMarcaDto ma);

    MarcaDto getMarcaById(int id);

    List<MarcaDto> getAllMarca();

    List<MarcaDtoBas> getAllMarcas();

    MarcaDto updateMarca(int id, CrearMarcaDto ma);

    void deleteMarca(int id);
}
