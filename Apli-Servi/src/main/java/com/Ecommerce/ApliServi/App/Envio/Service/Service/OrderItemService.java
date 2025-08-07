package com.Ecommerce.ApliServi.App.Envio.Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Envio.Dto.OrderItemDto;
import com.Ecommerce.ApliServi.App.Envio.Entity.OrderDetailEntity;
import com.Ecommerce.ApliServi.App.Envio.Entity.OrderItemEntity;
import com.Ecommerce.ApliServi.App.Envio.Repository.OrderDetailRepository;
import com.Ecommerce.ApliServi.App.Envio.Repository.OrderItemRepository;
import com.Ecommerce.ApliServi.App.Envio.Service.Interface.OrderIteminterface;
import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.ProductoRepository;

@Service
public class OrderItemService implements OrderIteminterface {
    private final OrderItemRepository orderItemRepository;
    private final ProductoRepository productoRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, ProductoRepository productoRepository,
            OrderDetailRepository orderDetailRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productoRepository = productoRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        OrderItemEntity orderItemEntity = mapToEntity(orderItemDto);
        OrderItemEntity savedEntity = orderItemRepository.save(orderItemEntity);
        return mapToDto(savedEntity);
    }

    @Override
    public OrderItemDto getOrderItemById(int id) {
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item not found with ID: " + id));
        return mapToDto(orderItemEntity);
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        List<OrderItemEntity> orderItemEntities = orderItemRepository.findAll();
        return orderItemEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto updateOrderItem(int id, OrderItemDto orderItemDto) {
        OrderItemEntity existingEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item not found with ID: " + id));

        // Actualizar los campos
        existingEntity.setQuantity(orderItemDto.getQuantity());
        existingEntity.setPrice(orderItemDto.getPrice());
        existingEntity.setProductName(orderItemDto.getProductName());

        // Guardar la entidad actualizada
        OrderItemEntity updatedEntity = orderItemRepository.save(existingEntity);
        return mapToDto(updatedEntity);
    }

    @Override
    public void deleteOrderItemById(int id) {
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item not found with ID: " + id));

        orderItemRepository.delete(orderItemEntity);
    }

    // Método para mapear de DTO a entidad
    private OrderItemEntity mapToEntity(OrderItemDto orderItemDto) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setQuantity(orderItemDto.getQuantity());
        orderItemEntity.setPrice(orderItemDto.getPrice());
        orderItemEntity.setProductName(orderItemDto.getProductName());
        // Suponiendo que tienes los repositorios necesarios para buscar las entidades
        // por ID
        OrderDetailEntity orderDetailEntity = orderDetailRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new RuntimeException(
                        "OrderDetailEntity not found with ID: " + orderItemDto.getOrderId()));

        ProductoEntity productoEntity = productoRepository.findById(orderItemDto.getProductId())
                .orElseThrow(
                        () -> new RuntimeException("ProductoEntity not found with ID: " + orderItemDto.getProductId()));

        orderItemEntity.setOrderDetail(orderDetailEntity);
        orderItemEntity.setProduct(productoEntity);
        return orderItemEntity;
    }

    // Método para mapear de entidad a DTO
    private OrderItemDto mapToDto(OrderItemEntity orderItemEntity) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItemEntity.getId());
        orderItemDto.setQuantity(orderItemEntity.getQuantity());
        orderItemDto.setPrice(orderItemEntity.getPrice());
        orderItemDto.setProductName(orderItemEntity.getProductName());
        orderItemDto.setOrderId(orderItemEntity.getOrderDetail().getId());
        orderItemDto.setProductId(orderItemEntity.getProduct().getId());
        return orderItemDto;
    }
}
