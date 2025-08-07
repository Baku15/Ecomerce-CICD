package com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.Respuesta;

public interface PageableQuery {
    Integer Pagina();

    Integer ElementosPorPagina();

    String OrdenadoPor();

    String EnOrden();
}
