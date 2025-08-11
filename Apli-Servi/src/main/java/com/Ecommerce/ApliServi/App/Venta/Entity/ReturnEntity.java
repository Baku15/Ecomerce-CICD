package com.Ecommerce.ApliServi.App.Venta.Entity;

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
@Table(name = "V_return")
public class ReturnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_return")
    private int idReturn;

    @Column(name = "return_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;

    @Column(name = "reason", length = 255)
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private UserEntity usuario;

    // Constructor, getters y setters automáticos gracias a Lombok
}
