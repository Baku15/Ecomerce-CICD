package com.Ecommerce.ApliServi.App.Producto.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Pr_Category")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category ")
    private int id;

    @Column(name = "name_category", nullable = false, length = 100)
    private String nombre;
    @Column(name = "description_category", nullable = false, length = 100)
    private String description;
    @ManyToMany(mappedBy = "categorias")
    private List<ProductoEntity> productos;

}
