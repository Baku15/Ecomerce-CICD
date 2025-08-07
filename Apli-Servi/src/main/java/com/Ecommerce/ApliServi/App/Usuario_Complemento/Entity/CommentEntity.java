package com.Ecommerce.ApliServi.App.Usuario_Complemento.Entity;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;

import jakarta.persistence.*;

import java.util.Date;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Us_Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "comment_date")
    @Temporal(TemporalType.DATE)
    private Date commentDate;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductoEntity product;
}
