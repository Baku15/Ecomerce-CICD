package com.Ecommerce.ApliServi.App.Producto.Entity;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.io.Serializable;

@Entity
@Table(name = "User_Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProduct {

    @EmbeddedId
    private UserProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductoEntity product;

    public UserProduct(UserEntity user, ProductoEntity product) {
        this.user = user;
        this.product = product;
        this.id = new UserProductId(user.getIdUsuario(), product.getId());
    }
}