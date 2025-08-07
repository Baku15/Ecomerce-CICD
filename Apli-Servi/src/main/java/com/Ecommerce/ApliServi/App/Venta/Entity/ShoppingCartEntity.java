package com.Ecommerce.ApliServi.App.Venta.Entity;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Shopping_Cart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_record_id", referencedColumnName = "id")
    private PurchaseRecordEntity purchaseRecordId;
}