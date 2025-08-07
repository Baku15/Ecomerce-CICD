package com.Ecommerce.ApliServi.App.Usuario_Complemento.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.CommentDto;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.Respuesta.PageableQuery;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Service.Interface.CommentInterface;

@RestController
@RequestMapping("/api")
public class CommentApi {
    private final CommentInterface commentService;

    @Autowired
    public CommentApi(CommentInterface commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<Respuesta> createComment(@RequestBody CommentDto commentDto) {
        try {
            CommentDto createdComment = commentService.save(commentDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Respuesta> getCommentById(@PathVariable int id) {
        try {
            CommentDto comment = commentService.findById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", comment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<Respuesta> getAllComments() {
        try {
            List<CommentDto> comments = commentService.findAll();
            return ResponseEntity.ok(new Respuesta("SUCCESS", comments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/comments/all")
    public ResponseEntity<List<CommentDto>> getAllComments(PageableQuery pageableQuery) {
        List<CommentDto> comments = commentService.findCommentDtos(pageableQuery);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/byProduct/{productId}")
    public ResponseEntity<?> getCommentsByProductId(@PathVariable int productId) {
        try {
            List<CommentDto> comments = commentService.getCommentsByProductId(productId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", comments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/comments/byUser/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable int userId) {
        List<CommentDto> comments = commentService.finCommentDtos(userId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Respuesta> updateComment(@PathVariable int id, @RequestBody CommentDto commentDto) {
        try {
            CommentDto updatedComment = commentService.update(id, commentDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Respuesta> deleteComment(@PathVariable int id) {
        try {
            commentService.deleteById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}