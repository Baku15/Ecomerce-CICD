package com.Ecommerce.ApliServi.App.Venta.Entity;

import java.math.BigDecimal;
import java.util.Date;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "V_sale")
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    private int idVenta;

    @Column(name = "quantity", nullable = false)
    private Integer cantidad;

    @Column(name = "sale_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;

    @Column(name = "price", nullable = false)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private UserEntity usuario;

    // Constructor, getters y setters
}