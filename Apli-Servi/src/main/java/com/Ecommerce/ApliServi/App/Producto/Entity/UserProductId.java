package com.Ecommerce.ApliServi.App.Producto.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserProductId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;

    public UserProductId() {}

    public UserProductId(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductId that = (UserProductId) o;
        return userId == that.userId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}