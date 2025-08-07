package com.Ecommerce.ApliServi.App.Envio.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Envio.Dto.OrderDetaildto;
import com.Ecommerce.ApliServi.App.Envio.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Envio.Service.Service.OrderDetailService;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailAPi {
    private OrderDetailService orderDetailService;

    @Autowired
    public void OrderDetailApi(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/create")
    public ResponseEntity<Respuesta> createOrderDetail(@RequestBody OrderDetaildto orderDetailDTO) {
        OrderDetaildto createdOrderDetail = orderDetailService.save(orderDetailDTO);
        return ResponseEntity.ok(new Respuesta("SUCCESS", createdOrderDetail));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getOrderDetailById(@PathVariable("id") int id) {
        try {
            OrderDetaildto orderDetailDTO = orderDetailService.findById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", orderDetailDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllOrderDetails() {
        try {
            List<OrderDetaildto> orderDetailDTOs = orderDetailService.findAll();
            return ResponseEntity.ok(new Respuesta("SUCCESS", orderDetailDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> updateOrderDetail(@PathVariable("id") int id,
            @RequestBody OrderDetaildto orderDetailDTO) {
        try {
            OrderDetaildto updatedOrderDetail = orderDetailService.update(id, orderDetailDTO);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta> deleteOrderDetail(@PathVariable("id") int id) {
        try {
            orderDetailService.deleteById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}
