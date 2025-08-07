package com.Ecommerce.ApliServi.App.Venta.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Venta.Dto.ShoppingCartDto;
import com.Ecommerce.ApliServi.App.Venta.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Venta.Service.Interface.ShoppingCarInterface;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCarApi {
    private final ShoppingCarInterface shoppingCarInterface;

    @Autowired
    public ShoppingCarApi(ShoppingCarInterface shoppingCarInterface) {
        this.shoppingCarInterface = shoppingCarInterface;
    }


    @PostMapping("/create")
    public ResponseEntity<Respuesta> createShoppingCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        try {
            ShoppingCartDto createdShoppingCart = shoppingCarInterface.createShoppingCart(shoppingCartDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdShoppingCart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Respuesta> getShoppingCartsByUserId(@PathVariable int userId) {
        try {
            List<ShoppingCartDto> shoppingCarts = shoppingCarInterface.getShoppingCartsByUserId(userId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", shoppingCarts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllShoppingCarts() {
        try {
            List<ShoppingCartDto> shoppingCarts = shoppingCarInterface.getAllShoppingCarts();
            return ResponseEntity.ok(new Respuesta("SUCCESS", shoppingCarts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta> deleteShoppingCartById(@PathVariable int id) {
        try {
            shoppingCarInterface.deleteShoppingCartById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }


    @PostMapping("/checkout")
    public ResponseEntity<Respuesta> realizarCompra(@RequestParam int userId) {
        try {
            shoppingCarInterface.realizarCompra(userId);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null, "Compra realizada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", null, e.getMessage()));
        }
    }
}
