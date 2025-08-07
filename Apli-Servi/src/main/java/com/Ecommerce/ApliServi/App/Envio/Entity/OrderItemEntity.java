package com.Ecommerce.ApliServi.App.Envio.Entity;

import jakarta.persistence.*;
import lombok.*;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;

import java.math.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Order_Item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordei_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "idOrder")
    private OrderDetailEntity orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductoEntity product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

}
