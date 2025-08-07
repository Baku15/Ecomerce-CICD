package com.Ecommerce.ApliServi.App.Venta.Entity;

import java.util.Date;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Purchase_Record")
public class PurchaseRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "purchase_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private UserEntity user;
}