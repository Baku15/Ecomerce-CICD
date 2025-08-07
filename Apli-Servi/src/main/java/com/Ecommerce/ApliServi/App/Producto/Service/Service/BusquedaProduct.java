package com.Ecommerce.ApliServi.App.Producto.Service.Service;


import com.Ecommerce.ApliServi.App.Producto.Dto.CategoriaDtoPro;
import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.AuxProDto1;
import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.AuxProDto2;
import com.Ecommerce.ApliServi.App.Producto.Dto.MarcaDtoPro;
import com.Ecommerce.ApliServi.App.Producto.Entity.CategoriaEntity;
import com.Ecommerce.ApliServi.App.Producto.Entity.MarcaEntity;
import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.CategoriaRepository;
import com.Ecommerce.ApliServi.App.Producto.Repository.DescuentoRepository;
import com.Ecommerce.ApliServi.App.Producto.Repository.MarcaRepository;
import com.Ecommerce.ApliServi.App.Producto.Repository.ProductoRepository;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.BusquedaInterface;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusquedaProduct implements BusquedaInterface {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    UserRepository usuarioRepository;
    @Autowired
    MarcaRepository marcaRepository;
    @Autowired
    DescuentoRepository descuentoRepository;
    @Override
    public List<CategoriaDtoPro> gCategoriaDtoPro(String nombreCategoria) {
        CategoriaEntity categoria = categoriaRepository.findByNombre(nombreCategoria)
                .orElseThrow(() -> new RuntimeException("No se encontró la categoría con el nombre: " + nombreCategoria));

        List<CategoriaDtoPro> categoriaDtoProList = new ArrayList<>();

        CategoriaDtoPro dto = new CategoriaDtoPro();
        dto.setNombre(categoria.getNombre());
        List<AuxProDto2> auxProDto2List = categoria.getProductos().stream()
                .map(this::mapProductoToAuxProDto2)
                .collect(Collectors.toList());
        dto.setProDto2List(auxProDto2List);
        categoriaDtoProList.add(dto);

        return categoriaDtoProList;
    }

    private AuxProDto2 mapProductoToAuxProDto2(ProductoEntity producto) {
        AuxProDto2 auxProDto2 = new AuxProDto2();
        auxProDto2.setNombre(producto.getNombre());
        auxProDto2.setDescripcion(producto.getDescripcion());
        auxProDto2.setImageUrl(producto.getImageUrl());
        auxProDto2.setStock(producto.getStock());
        auxProDto2.setPrecio(producto.getPrecio());
        auxProDto2.setMarca(producto.getMarca().getId());
        auxProDto2.setUsuarios(producto.getUsuarios().stream().map(UserEntity::getIdUsuario).collect(Collectors.toList()));
        return auxProDto2;
    }

    @Override
    public List<MarcaDtoPro> gMarcaDtoPro(String g) {
        MarcaEntity marca = marcaRepository.findByNombre(g)
                .orElseThrow(() -> new RuntimeException("No se encontró la marca con el nombre: " + g));

        List<MarcaDtoPro> marcaDtoProList = new ArrayList<>();

        MarcaDtoPro dto = new MarcaDtoPro();
        dto.setNombre(marca.getNombre());
        List<AuxProDto1> auxProDto1List = marca.getUsuarios().stream() // Aquí deberías usar getProductos() en lugar de getUsuarios()
                .map(this::mapProductoToAuxProDto1)
                .collect(Collectors.toList());
        dto.setProDto1(auxProDto1List);
        marcaDtoProList.add(dto);

        return marcaDtoProList;
    }
    @Override
    public Page<CategoriaDtoPro> gCategoriaDtoPro(String nombreCategoria, Pageable pageable) {
        CategoriaEntity categoria = categoriaRepository.findByNombre(nombreCategoria)
                .orElseThrow(() -> new RuntimeException("No se encontró la categoría con el nombre: " + nombreCategoria));

        CategoriaDtoPro dto = new CategoriaDtoPro();
        dto.setNombre(categoria.getNombre());
        List<AuxProDto2> auxProDto2List = categoria.getProductos().stream()
                .map(this::mapProductoToAuxProDto2)
                .collect(Collectors.toList());
        dto.setProDto2List(auxProDto2List);

        return new PageImpl<>(Collections.singletonList(dto), pageable, 1); // Devolver una sola página con un solo elemento
    }


    @Override
    public Page<MarcaDtoPro> gMarcaDtoPro(String g, Pageable pageable) {
        MarcaEntity marca = marcaRepository.findByNombre(g)
                .orElseThrow(() -> new RuntimeException("No se encontró la marca con el nombre: " + g));

        MarcaDtoPro dto = new MarcaDtoPro();
        dto.setNombre(marca.getNombre());
        List<AuxProDto1> auxProDto1List = marca.getUsuarios().stream() // Utilizar getProductos() en lugar de getUsuarios()
                .map(this::mapProductoToAuxProDto1)
                .collect(Collectors.toList());
        dto.setProDto1(auxProDto1List);

        return new PageImpl<>(Collections.singletonList(dto), pageable, 1); // Devolver una sola página con un solo elemento
    }
   private AuxProDto1 mapProductoToAuxProDto1(ProductoEntity producto) {
        AuxProDto1 auxProDto1 = new AuxProDto1();
        auxProDto1.setNombre(producto.getNombre());
        auxProDto1.setDescripcion(producto.getDescripcion());
        auxProDto1.setImageUrl(producto.getImageUrl());
        auxProDto1.setStock(producto.getStock());
        auxProDto1.setUsuarios(producto.getUsuarios().stream().map(UserEntity::getIdUsuario).collect(Collectors.toList()));
        auxProDto1.setPrecio(producto.getPrecio());
        auxProDto1.setCategorias(producto.getCategorias().stream().map(CategoriaEntity::getId).collect(Collectors.toList()));
        return auxProDto1;
   }
}
