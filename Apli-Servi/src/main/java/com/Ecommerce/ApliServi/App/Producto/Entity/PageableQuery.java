package com.Ecommerce.ApliServi.App.Producto.Entity;

public class PageableQuery {
    private int pagina;
    private int elementosPorPagina;
    private String ordenadoPor;
    private String enOrden;

    public PageableQuery(int pagina, int elementosPorPagina, String ordenadoPor, String enOrden) {
        this.pagina = pagina;
        this.elementosPorPagina = elementosPorPagina;
        this.ordenadoPor = ordenadoPor;
        this.enOrden = enOrden;
    }

    // Getters y setters
    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getElementosPorPagina() {
        return elementosPorPagina;
    }

    public void setElementosPorPagina(int elementosPorPagina) {
        this.elementosPorPagina = elementosPorPagina;
    }

    public String getOrdenadoPor() {
        return ordenadoPor;
    }

    public void setOrdenadoPor(String ordenadoPor) {
        this.ordenadoPor = ordenadoPor;
    }

    public String getEnOrden() {
        return enOrden;
    }

    public void setEnOrden(String enOrden) {
        this.enOrden = enOrden;
    }
}