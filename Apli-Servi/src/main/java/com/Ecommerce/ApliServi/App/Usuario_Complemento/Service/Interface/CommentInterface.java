package com.Ecommerce.ApliServi.App.Usuario_Complemento.Service.Interface;

import java.util.*;

import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.CommentDto;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.Respuesta.PageableQuery;

public interface CommentInterface {
    CommentDto save(CommentDto dto);

    CommentDto findById(int id);

    List<CommentDto> findAll();

    CommentDto update(int id, CommentDto dto);

    List<CommentDto> findCommentDtos(PageableQuery pageableQuery);

    List<CommentDto> finCommentDtos(int id);

    void deleteById(int id);

    List<CommentDto> getCommentsByProductId(int productId);

}
