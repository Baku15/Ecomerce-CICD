package com.Ecommerce.ApliServi.App.Producto.Dto.Respuesta;

public interface PageableQuery {
    Integer getPagina();
    Integer getElementosPorPagina();
    String getOrdenadoPor();
    String getEnOrden();

    // Opcional: puedes eliminar los siguientes métodos si no son necesarios
    int elementosPorPagina();
    int pagina();
}
