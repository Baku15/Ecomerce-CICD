package com.Ecommerce.ApliServi.App.Venta.Dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShoppingCartDto {
    private int id;
    private int quantity;
    private int productId;
    private int purchaseRecordId;
    private int userId;  // Nuevo campo para el ID del usuario
    private String productName; // Añade este campo
    private BigDecimal productPrice; // Añade este campo

}