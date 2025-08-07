package com.Ecommerce.ApliServi.App.Envio.Dto.Respuesta;

public interface PageableQuery {
    Integer Pagina();

    Integer ElementosPorPagina();

    String OrdenadoPor();

    String EnOrden();
}
