package com.Ecommerce.ApliServi.App.Venta.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Venta.Dto.ShoppingCartDto;

public interface ShoppingCarInterface {
    ShoppingCartDto createShoppingCart(ShoppingCartDto shoppingCartDto);

    ShoppingCartDto getShoppingCartById(int id);

    List<ShoppingCartDto> getAllShoppingCarts();

    void deleteShoppingCartById(int id);
    List<ShoppingCartDto> getShoppingCartsByUserId(int userId); // Añade este método

    void realizarCompra(int userId); // Añadir este método

}
