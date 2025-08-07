package com.Ecommerce.ApliServi.App.Producto.Service.Service;


import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.AutoDto;
import com.Ecommerce.ApliServi.App.Producto.Entity.Auto;
import com.Ecommerce.ApliServi.App.Producto.Entity.PageableQuery;
import com.Ecommerce.ApliServi.App.Producto.Repository.AutoRepository;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AutoServiceImpl implements AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public List<AutoDto> getAllAutos(PageableQuery pageableQuery) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageableQuery.getEnOrden()), pageableQuery.getOrdenadoPor());
        PageRequest pageable = PageRequest.of(pageableQuery.getPagina(), pageableQuery.getElementosPorPagina(), sort);
        Page<Auto> autoPage = autoRepository.findAll(pageable);
        return autoPage.getContent().stream().map(this::mapToDto).collect(Collectors.toList());
    }


    @Override
    public Page<AutoDto> getAllAutos(Pageable pageable) {
        Page<Auto> autoPage = autoRepository.findAll(pageable);
        return autoPage.map(this::mapToDto);
    }

    public AutoDto createAuto(AutoDto autoDto) {
        Auto auto = new Auto();
        auto.setMarca(autoDto.getMarca());
        auto.setModelo(autoDto.getModelo());
        auto.setYear(autoDto.getYear());
        Auto savedAuto = autoRepository.save(auto);
        return mapToDto(savedAuto);
    }

    private AutoDto mapToDto(Auto auto) {
        AutoDto dto = new AutoDto();
        dto.setId(auto.getId());
        dto.setMarca(auto.getMarca());
        dto.setModelo(auto.getModelo());
        dto.setYear(auto.getYear());
        return dto;
    }
}