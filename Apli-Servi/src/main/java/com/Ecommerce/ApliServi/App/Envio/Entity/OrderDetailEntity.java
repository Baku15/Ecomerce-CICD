package com.Ecommerce.ApliServi.App.Envio.Entity;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Order_Detail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrder")
    private int id;

    @Column(name = "start_date", nullable = false, length = 250)
    private Date fechaini;

    @Column(name = "delivery_date", nullable = false, length = 250)
    private Date fechafin;

    @Column(name = "shipping_status", nullable = false, length = 500)
    private String estadienvio;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user", nullable = false)
    private UserEntity user;

}
