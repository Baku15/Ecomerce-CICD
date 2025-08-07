package com.Ecommerce.ApliServi.App.Envio.Dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private String productName;
}
