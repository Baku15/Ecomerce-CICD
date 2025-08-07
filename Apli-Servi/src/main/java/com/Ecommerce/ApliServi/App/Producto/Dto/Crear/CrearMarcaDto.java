package com.Ecommerce.ApliServi.App.Producto.Dto.Crear;

import org.springframework.web.multipart.MultipartFile;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CrearMarcaDto {
    private String marca;
    private String Descripcion;
    private MultipartFile imagen;
}
