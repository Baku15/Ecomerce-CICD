package com.Ecommerce.ApliServi.App.Producto.Service.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Producto.Dto.Basico.MarcaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.Crear.CrearMarcaDto;
import com.Ecommerce.ApliServi.App.Producto.Dto.IndiceBas.MarcaDtoBas;
import com.Ecommerce.ApliServi.App.Producto.Entity.MarcaEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.MarcaRepository;
import com.Ecommerce.ApliServi.App.Producto.Service.Interface.MarcaInterface;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

@Service
public class MarcaService implements MarcaInterface {

    @Autowired
    MarcaRepository marcaRepository;
    @Autowired
    private MinioClient minioClient;

    private static final String BUCKET_NAME = "1-marca";

    // Método para crear una nueva marca
    public MarcaDto createMarcaDto(CrearMarcaDto ma) {
        try {
            // Crear un nuevo objeto MarcaDto
            MarcaDto marcaDto = new MarcaDto();
            marcaDto.setNombre(ma.getMarca()); // Asegúrate de que ma.getMarca() no devuelve null
            marcaDto.setDescripcion(ma.getDescripcion()); // Asegúrate de que ma.getDescripcion() no devuelve null

            // Verificar si la imagen no es nula y obtener la información necesaria
            if (ma.getImagen() != null && !ma.getImagen().isEmpty()) {
                String nombreImagen = ma.getImagen().getOriginalFilename();
                InputStream inputStream = ma.getImagen().getInputStream();
                saveImage(inputStream, nombreImagen);
                String urlImagen = getUrlImagen(nombreImagen);
                marcaDto.setImagen(urlImagen);
            } else {
                throw new RuntimeException("El Imagen nulo o vacío.");
            }

            // Mapear el DTO de marca a la entidad de marca
            MarcaEntity marcaEntity = mapToEntity(marcaDto);

            // Verificar que todas las propiedades requeridas de marcaEntity estén presentes
            if (marcaEntity.getNombre() == null || marcaEntity.getNombre().isEmpty()) {
                throw new RuntimeException("El nombre de la marca no puede ser nulo o vacío.");
            }

            // Guardar la entidad de marca en la base de datos
            marcaEntity = marcaRepository.save(marcaEntity);

            // Mapear la entidad de marca a DTO de marca y devolverla
            return mapToDto(marcaEntity);
        } catch (IOException e) {
            throw new RuntimeException("Error al crear la marca: " + e.getMessage());
        }
    }

    // Método para obtener una marca por su ID
    public MarcaDto getMarcaById(int id) {
        MarcaEntity marcaEntity = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + id));
        return mapToDto(marcaEntity);
    }

    // Método para obtener todas las marcas
    public List<MarcaDto> getAllMarca() {
        return marcaRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Método para actualizar una marca existente
    public MarcaDto updateMarca(int id, CrearMarcaDto ma) {
        MarcaEntity existingMarca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + id));

        // Actualizar los campos de la marca con los datos del DTO
        existingMarca.setNombre(ma.getMarca());
        existingMarca.setDescripcion(ma.getDescripcion());

        // Si se proporciona una nueva imagen, eliminar la imagen anterior y guardar la
        // nueva
        if (ma.getImagen() != null) {
            try {
                // Obtener nombre de la imagen
                String nombreImagen = ma.getImagen().getOriginalFilename();
                // Obtener flujo de entrada de la imagen
                InputStream inputStream = ma.getImagen().getInputStream();
                // Guardar la nueva imagen en Minio
                saveImage(inputStream, nombreImagen);
                // Eliminar la imagen anterior
                deleteImage(existingMarca.getImageUrl());
                // Actualizar la URL de la imagen en la entidad de marca
                existingMarca.setImageUrl(nombreImagen);
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen: " + e.getMessage());
            }
        }

        // Guardar los cambios en la base de datos
        marcaRepository.save(existingMarca);

        // Mapear la entidad de marca actualizada a DTO y devolverla
        return mapToDto(existingMarca);
    }

    // Método para eliminar una marca por su ID
    public void deleteMarca(int id) {
        MarcaEntity marcaEntity = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + id));
        // Eliminar la imagen asociada a la marca
        deleteImage(marcaEntity.getImageUrl());
        // Eliminar la marca de la base de datos
        marcaRepository.delete(marcaEntity);
    }

    // Método para obtener todas las marcas y mapearlas a MarcaDtoBas
    public List<MarcaDtoBas> getAllMarcas() {
        List<MarcaEntity> marcas = marcaRepository.findAll();
        return marcas.stream()
                .map(this::mapToDto1)
                .collect(Collectors.toList());
    }

    // Método auxiliar para mapear de Entity a MarcaDtoBas
    private MarcaDtoBas mapToDto1(MarcaEntity marcaEntity) {
        MarcaDtoBas marcaDtoBas = new MarcaDtoBas();
        marcaDtoBas.setId(marcaEntity.getId());
        marcaDtoBas.setNombre(marcaEntity.getNombre());
        return marcaDtoBas;
    }

    // Método auxiliar para mapear de Entity a DTO
    private MarcaDto mapToDto(MarcaEntity marcaEntity) {
        MarcaDto marcaDto = new MarcaDto();
        marcaDto.setId(marcaEntity.getId());
        marcaDto.setNombre(marcaEntity.getNombre());
        marcaDto.setDescripcion(marcaEntity.getDescripcion());
        marcaDto.setImagen(marcaEntity.getImageUrl());
        return marcaDto;
    }

    // Método auxiliar para mapear de DTO a Entity
    private MarcaEntity mapToEntity(MarcaDto marcaDto) {
        MarcaEntity marcaEntity = new MarcaEntity();
        marcaEntity.setNombre(marcaDto.getNombre());
        marcaEntity.setDescripcion(marcaDto.getDescripcion());
        marcaEntity.setImageUrl(marcaDto.getImagen());
        return marcaEntity;
    }

    // Método auxiliar para guardar la imagen en Minio
    private void saveImage(InputStream inputStream, String objectName) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen de la marca: " + e.getMessage());
        }
    }

    // Método auxiliar para eliminar la imagen de Minio
    private void deleteImage(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la imagen de la marca: " + e.getMessage());
        }
    }

    // Método auxiliar para obtener la URL de la imagen desde Minio
    private String getUrlImagen(String objectName) {
        try {
            // Construir y devolver la URL de la imagen
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la URL de la imagen: " + e.getMessage());
        }
    }
}
