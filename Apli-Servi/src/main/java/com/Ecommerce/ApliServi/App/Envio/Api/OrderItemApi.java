package com.Ecommerce.ApliServi.App.Envio.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Envio.Dto.OrderItemDto;
import com.Ecommerce.ApliServi.App.Envio.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Envio.Service.Interface.OrderIteminterface;
import com.Ecommerce.ApliServi.App.Envio.Service.Service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/order-item")
public class OrderItemApi {
    private final OrderIteminterface orderItemService;

    @Autowired
    public OrderItemApi(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<Respuesta> createOrderItem(@RequestBody OrderItemDto orderItemDto) {
        try {
            OrderItemDto createdOrderItem = orderItemService.createOrderItem(orderItemDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdOrderItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getOrderItemById(@PathVariable("id") int id) {
        try {
            OrderItemDto orderItemDto = orderItemService.getOrderItemById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", orderItemDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllOrderItems() {
        try {
            List<OrderItemDto> orderItemDtos = orderItemService.getAllOrderItems();
            return ResponseEntity.ok(new Respuesta("SUCCESS", orderItemDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> updateOrderItem(@PathVariable("id") int id,
            @RequestBody OrderItemDto orderItemDto) {
        try {
            OrderItemDto updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedOrderItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta> deleteOrderItem(@PathVariable("id") int id) {
        try {
            orderItemService.deleteOrderItemById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}
