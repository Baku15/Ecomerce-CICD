package com.Ecommerce.ApliServi.App.Producto.Entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pr_Product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id")
        private int id;

        @Column(name = "name", nullable = false, length = 100)
        private String nombre;

        @Column(name = "description", nullable = false, length = 500)
        private String descripcion;

        @Column(name = "url_img", nullable = false, length = 500)
        private String imageUrl;

        @Column(name = "stock", nullable = false)
        private Integer stock;

        @Column(name = "price", precision = 10, scale = 2, nullable = false)
        private BigDecimal precio;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "brand_id")
        private MarcaEntity marca;
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "Product_Category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
        private List<CategoriaEntity> categorias = new ArrayList<>();
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "User_Product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<UserEntity> usuarios = new ArrayList<>();
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "Product_Discount", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "discount_id"))
        private List<DescuentoEntity> discounts = new ArrayList<>();
}
