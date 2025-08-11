package com.Ecommerce.ApliServi.App.Producto.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Discounts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DescuentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private int id;

    @Column(name = "percentage", nullable = false)
    private double percentage;

    public void setPercentage(BigDecimal bigDecimal) {
    }
}
