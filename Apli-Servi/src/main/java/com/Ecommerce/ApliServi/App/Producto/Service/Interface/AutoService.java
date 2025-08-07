package com.Ecommerce.ApliServi.App.Producto.Service.Interface;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.AutoDto;
import com.Ecommerce.ApliServi.App.Producto.Entity.PageableQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AutoService {
    List<AutoDto> getAllAutos(PageableQuery pageableQuery);
    AutoDto createAuto(AutoDto autoDto); // Nuevo método para crear un auto

    Page<AutoDto> getAllAutos(Pageable pageable);


}
