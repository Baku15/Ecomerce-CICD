package com.Ecommerce.ApliServi.App.Producto.Service.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.CategoriaDto;
import com.Ecommerce.ApliServi.App.Producto.Entity.CategoriaEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.CategoriaRepository;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.CategoriaInterface;

@Service
public class CategoriaService implements CategoriaInterface {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Método para crear una nueva categoría
    public CategoriaDto createCategoria(CategoriaDto categoriaDto) {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre(categoriaDto.getCategoria());
        categoriaEntity.setDescription(categoriaDto.getDescripcion());
        categoriaRepository.save(categoriaEntity);
        // Puedes retornar el DTO creado o una versión actualizada con el ID generado
        return categoriaDto;
    }

    // Método para obtener una categoría por su ID
    public CategoriaDto getCategoriaById(int id) {
        Optional<CategoriaEntity> categoriaOptional = categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            CategoriaEntity categoriaEntity = categoriaOptional.get();
            return entityToDto(categoriaEntity);
        } else {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
    }

    // Método para obtener todas las categorías
    public List<CategoriaDto> getAllCategorias() {
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        // Mapea las entidades a DTOs y las devuelve como una lista
        return categorias.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    // Método para actualizar una categoría existente
    public CategoriaDto updateCategoria(int id, CategoriaDto categoriaDto) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        // Actualiza los campos relevantes
        categoriaEntity.setNombre(categoriaDto.getCategoria());
        categoriaEntity.setDescription(categoriaDto.getDescripcion());
        categoriaRepository.save(categoriaEntity);

        // Retorna el DTO actualizado
        return categoriaDto;
    }

    // Método para eliminar una categoría por su ID
    public void deleteCategoria(int id) {
        categoriaRepository.deleteById(id);
    }

    // Método auxiliar para convertir de Entity a DTO
    private CategoriaDto entityToDto(CategoriaEntity categoriaEntity) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setIdCategoria(categoriaEntity.getId());
        categoriaDto.setCategoria(categoriaEntity.getNombre());
        categoriaDto.setDescripcion(categoriaEntity.getDescription());
        return categoriaDto;
    }
}
