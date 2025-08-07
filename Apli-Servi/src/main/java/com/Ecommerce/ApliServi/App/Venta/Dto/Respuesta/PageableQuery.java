package com.Ecommerce.ApliServi.App.Venta.Dto.Respuesta;

public interface PageableQuery {
    Integer Pagina();

    Integer ElementosPorPagina();

    String OrdenadoPor();

    String EnOrden();
}
