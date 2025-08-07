package com.Ecommerce.ApliServi.App.Usuario.Entity;

import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.EAutorizacion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "US_authorization")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorization_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private EAutorizacion permiso;
}
