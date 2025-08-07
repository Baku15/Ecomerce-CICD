package com.Ecommerce.ApliServi.App.Usuario_Complemento.Service.Interface;

import java.util.*;

import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.AddressDto;

public interface AddressInterface {
    AddressDto save(AddressDto dto);

    AddressDto findById(int id);

    List<AddressDto> findAll();

    AddressDto update(int id, AddressDto dto);

    void deleteById(int id);
}
