package com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Respuesta {
    private String code;
    private Object result;
    private String message;

    public Respuesta(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Respuesta(String code, Object result) {
        this.code = "SUCCESS";
        this.result = result;
    }

}
