package com.Ecommerce.ApliServi.App.Envio.Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Envio.Dto.OrderDetaildto;
import com.Ecommerce.ApliServi.App.Envio.Entity.OrderDetailEntity;
import com.Ecommerce.ApliServi.App.Envio.Repository.OrderDetailRepository;
import com.Ecommerce.ApliServi.App.Envio.Service.Interface.OrderDetailinterface;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;

@Service
public class OrderDetailService implements OrderDetailinterface {
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, UserRepository userRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderDetaildto save(OrderDetaildto orderDetailDTO) {
        OrderDetailEntity orderDetailEntity = convertToEntity(orderDetailDTO);
        OrderDetailEntity savedEntity = orderDetailRepository.save(orderDetailEntity);
        return convertToDTO(savedEntity);
    }

    @Override
    public OrderDetaildto findById(int id) {
        OrderDetailEntity orderDetailEntity = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order detail not found with id: " + id));
        return convertToDTO(orderDetailEntity);
    }

    @Override
    public List<OrderDetaildto> findAll() {
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findAll();
        return orderDetailEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetaildto update(int id, OrderDetaildto orderDetailDTO) {
        OrderDetailEntity existingEntity = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order detail not found with id: " + id));
        OrderDetailEntity updatedEntity = convertToEntity(orderDetailDTO);
        updatedEntity.setId(existingEntity.getId()); // Ensure the ID is set to the existing one
        OrderDetailEntity savedEntity = orderDetailRepository.save(updatedEntity);
        return convertToDTO(savedEntity);
    }

    @Override
    public void deleteById(int id) {
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailEntity convertToEntity(OrderDetaildto orderDetailDTO) {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setId(orderDetailDTO.getId());
        orderDetailEntity.setFechaini(orderDetailDTO.getStartDate());
        orderDetailEntity.setFechafin(orderDetailDTO.getDeliveryDate());
        orderDetailEntity.setEstadienvio(orderDetailDTO.getShippingStatus());
        // Set user manually
        // Asignar el usuario a la entidad
        UserEntity user = userRepository.findById(orderDetailDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + orderDetailDTO.getUserId()));
        orderDetailEntity.setUser(user);
        return orderDetailEntity;
    }

    private OrderDetaildto convertToDTO(OrderDetailEntity orderDetailEntity) {
        OrderDetaildto orderDetailDTO = new OrderDetaildto();
        orderDetailDTO.setId(orderDetailEntity.getId());
        orderDetailDTO.setStartDate(orderDetailEntity.getFechaini());
        orderDetailDTO.setDeliveryDate(orderDetailEntity.getFechafin());
        orderDetailDTO.setShippingStatus(orderDetailEntity.getEstadienvio());
        /// Asignar el id del usuario al DTO
        orderDetailDTO.setUserId(orderDetailEntity.getUser().getIdUsuario());
        return orderDetailDTO;
    }
}
