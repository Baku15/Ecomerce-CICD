package com.Ecommerce.ApliServi.App.Venta.Dto.Respuesta;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Respuesta {
    private String code;
    private Object result;
    private String message;

    // Constructor con todos los campos
    public Respuesta(String code, Object result, String message) {
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public Respuesta(String code, Object result) {
        this.code = "SUCCESS";
        this.result = result;
    }

}
