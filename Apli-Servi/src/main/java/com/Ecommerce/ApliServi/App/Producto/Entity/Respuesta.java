package com.Ecommerce.ApliServi.App.Producto.Entity;


public class Respuesta {
    private String code;
    private Object result;

    public Respuesta(String code, Object result) {
        this.code = code;
        this.result = result;
    }

    // Getters y setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
