package com.Ecommerce.ApliServi.App.Envio.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Envio.Dto.OrderItemDto;

public interface OrderIteminterface {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);

    OrderItemDto getOrderItemById(int id);

    List<OrderItemDto> getAllOrderItems();

    void deleteOrderItemById(int id);

    OrderItemDto updateOrderItem(int id, OrderItemDto orderItemDto);
}