package com.Ecommerce.ApliServi.App.Venta.Dto;

import java.util.Date;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseRecordDto {
    private int id;
    private Date purchaseDate;
    private int userId;
}
