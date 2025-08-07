package com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta;

public interface PageableQuery {
    Integer Pagina();

    Integer ElementosPorPagina();

    String OrdenadoPor();

    String EnOrden();
}
