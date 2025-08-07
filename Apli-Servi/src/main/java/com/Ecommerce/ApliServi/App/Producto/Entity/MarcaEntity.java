package com.Ecommerce.ApliServi.App.Producto.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pr_Brand")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MarcaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private int id;

    @Column(name = "brand_name", nullable = false, length = 100)
    private String nombre;

    @Column(name = "description", nullable = false, length = 250)
    private String descripcion;

    @Column(name = "url_img", nullable = false, length = 500)
    private String imageUrl;
    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoEntity> usuarios;
}
