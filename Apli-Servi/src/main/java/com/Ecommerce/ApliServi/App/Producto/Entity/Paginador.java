package com.Ecommerce.ApliServi.App.Producto.Entity;
import java.util.ArrayList;
import java.util.List;

public class Paginador<T> {
    private List<T> elementos;
    private int elementosPorPagina;

    public Paginador(List<T> elementos, int elementosPorPagina) {
        this.elementos = elementos;
        this.elementosPorPagina = elementosPorPagina;
    }

    public List<T> obtenerPagina(int pagina) {
        int desde = pagina * elementosPorPagina;
        int hasta = Math.min(desde + elementosPorPagina, elementos.size());
        if (desde >= hasta) {
            return new ArrayList<>();
        }
        return elementos.subList(desde, hasta);
    }
}