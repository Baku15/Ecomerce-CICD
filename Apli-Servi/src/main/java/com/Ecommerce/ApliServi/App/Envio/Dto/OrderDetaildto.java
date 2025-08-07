package com.Ecommerce.ApliServi.App.Envio.Dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetaildto {
    private int id;
    private Date startDate;
    private Date deliveryDate;
    private String shippingStatus;
    private int userId;
}
