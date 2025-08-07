package com.Ecommerce.ApliServi.App.Venta.Service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.ProductoRepository;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Venta.Dto.ventaDto;
import com.Ecommerce.ApliServi.App.Venta.Entity.VentaEntity;
import com.Ecommerce.ApliServi.App.Venta.Repository.VentaRepository;
import com.Ecommerce.ApliServi.App.Venta.Service.Interface.VentaInterface;



@Service
public class ServiciosVenta implements VentaInterface {
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final UserRepository userRepository;

    @Autowired
    public ServiciosVenta(VentaRepository ventaRepository, ProductoRepository productoRepository, UserRepository userRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ventaDto createVenta(ventaDto ventaDto) {
        ProductoEntity productoEntity = productoRepository.findById(ventaDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("El producto con ID " + ventaDto.getProductoId() + " no existe."));
        UserEntity usuarioEntity = userRepository.findById(ventaDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + ventaDto.getUsuarioId() + " no existe."));
        VentaEntity ventaEntity = mapToEntity(ventaDto, productoEntity, usuarioEntity);
        return mapToDto(ventaRepository.save(ventaEntity));
    }

    @Override
    public ventaDto getVentaById(int id) {
        VentaEntity ventaEntity = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        return mapToDto(ventaEntity);
    }

    @Override
    public List<ventaDto> getAllVentas() {
        return ventaRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ventaDto> getVentasByUserId(int userId) {
        return ventaRepository.findByUsuario_IdUsuario(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ventaDto> getVentasByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        return ventaRepository.findByUsuario_IdUsuarioAndFechaVentaBetween(userId, startDate, endDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ventaDto> getVentasByUserIdAndProductId(int userId, int productId) {
        List<VentaEntity> ventas = ventaRepository.findByUsuario_IdUsuarioAndProducto_Id(userId, productId);
        return ventas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<ventaDto> getVentasByUserIdAndCategoryId(int userId, int categoryId) {
        List<VentaEntity> ventas = ventaRepository.findByUsuario_IdUsuarioAndProducto_Categorias_Id(userId, categoryId);
        return ventas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private VentaEntity mapToEntity(ventaDto ventaDto, ProductoEntity productoEntity, UserEntity usuarioEntity) {
        VentaEntity ventaEntity = new VentaEntity();
        ventaEntity.setCantidad(ventaDto.getCantidad());
        ventaEntity.setFechaVenta(ventaDto.getFechaVenta());
        ventaEntity.setPrecio(ventaDto.getPrecio());
        ventaEntity.setProducto(productoEntity);
        ventaEntity.setUsuario(usuarioEntity);
        return ventaEntity;
    }

    private ventaDto mapToDto(VentaEntity ventaEntity) {
        ventaDto ventaDto = new ventaDto();
        ventaDto.setId(ventaEntity.getIdVenta());
        ventaDto.setCantidad(ventaEntity.getCantidad());
        ventaDto.setFechaVenta(ventaEntity.getFechaVenta());
        ventaDto.setPrecio(ventaEntity.getPrecio());
        ventaDto.setProductoId(ventaEntity.getProducto().getId());
        ventaDto.setUsuarioId(ventaEntity.getUsuario().getIdUsuario());
        ventaDto.setProductoNombre(ventaEntity.getProducto().getNombre());
        return ventaDto;
    }

    public Page<ventaDto> getVentasByUserIdPaginated(int userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<VentaEntity> ventaPage = ventaRepository.findByUsuario_IdUsuario(userId, pageRequest);
        return ventaPage.map(this::mapToDto);
    }

    public Page<ventaDto> searchVentasByUserId(int userId, String nombreProducto, Pageable pageable) {
        Page<VentaEntity> ventaPage = ventaRepository.findByUsuario_IdUsuarioAndProducto_NombreContainingIgnoreCase(userId, nombreProducto, pageable);
        return ventaPage.map(this::mapToDto);
    }

    public Page<ventaDto> getSalesPaginated(int userId, Pageable pageable) {
        Page<VentaEntity> ventas = ventaRepository.findByUsuario_IdUsuario(userId, pageable);
        return ventas.map(this::mapToDto);
    }
}