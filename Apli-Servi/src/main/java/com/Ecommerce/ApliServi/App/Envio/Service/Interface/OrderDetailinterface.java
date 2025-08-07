package com.Ecommerce.ApliServi.App.Envio.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Envio.Dto.OrderDetaildto;

public interface OrderDetailinterface {
    OrderDetaildto save(OrderDetaildto orderDetailDTO);

    OrderDetaildto findById(int id);

    List<OrderDetaildto> findAll();

    OrderDetaildto update(int id, OrderDetaildto orderDetailDTO);

    void deleteById(int id);
}
