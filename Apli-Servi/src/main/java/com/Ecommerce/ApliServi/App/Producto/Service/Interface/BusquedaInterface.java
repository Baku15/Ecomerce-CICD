package com.Ecommerce.ApliServi.App.Producto.Service.Interface;

import com.Ecommerce.ApliServi.App.Producto.Dto.CategoriaDtoPro;
import com.Ecommerce.ApliServi.App.Producto.Dto.MarcaDtoPro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusquedaInterface {

    List<CategoriaDtoPro> gCategoriaDtoPro(String g);

    List<MarcaDtoPro> gMarcaDtoPro(String g);
    Page<MarcaDtoPro> gMarcaDtoPro(String g, Pageable pageable);
    Page<CategoriaDtoPro> gCategoriaDtoPro(String nombreCategoria, Pageable pageable);
}
