package com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto;

import java.util.Date;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class CommentDto {
    private int id;
    private String comentario;
    private Date fechaComentario;
    private int puntuacion;
    private int id_user;
    private int id_producto;
    private String userName; // Añadir este campo


}
