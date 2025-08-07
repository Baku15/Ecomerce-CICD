package com.Ecommerce.ApliServi.App.Usuario_Complemento.Entity;

import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Us_address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "people_id", nullable = false)
    private PersonaEntity people;
}
