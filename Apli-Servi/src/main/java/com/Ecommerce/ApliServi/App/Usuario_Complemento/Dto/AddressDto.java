package com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddressDto {
    private int addressId;
    private String address;
    private String city;
    private String postalCode;
    private int peopleId;

}
