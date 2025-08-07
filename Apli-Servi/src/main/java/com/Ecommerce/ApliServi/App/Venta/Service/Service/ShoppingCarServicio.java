package com.Ecommerce.ApliServi.App.Venta.Service.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Venta.Entity.VentaEntity;
import com.Ecommerce.ApliServi.App.Venta.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Producto.Entity.ProductoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.ProductoRepository;
import com.Ecommerce.ApliServi.App.Venta.Dto.ShoppingCartDto;
import com.Ecommerce.ApliServi.App.Venta.Entity.PurchaseRecordEntity;
import com.Ecommerce.ApliServi.App.Venta.Entity.ShoppingCartEntity;
import com.Ecommerce.ApliServi.App.Venta.Repository.PurchaseRecordRepository;
import com.Ecommerce.ApliServi.App.Venta.Repository.ShoppingCartRepository;
import com.Ecommerce.ApliServi.App.Venta.Service.Interface.ShoppingCarInterface;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingCarServicio implements ShoppingCarInterface {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductoRepository productoRepository;
    private final PurchaseRecordRepository purchaseRecordRepository;
    private final UserRepository userRepository;
    private final VentaRepository ventaRepository;



    @Autowired
    public ShoppingCarServicio(ShoppingCartRepository shoppingCartRepository, ProductoRepository productoRepository,
                               PurchaseRecordRepository purchaseRecordRepository, UserRepository userRepository,
                               VentaRepository ventaRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productoRepository = productoRepository;
        this.purchaseRecordRepository = purchaseRecordRepository;
        this.userRepository = userRepository;
        this.ventaRepository = ventaRepository;
    }

    @Transactional
    public void realizarCompra(int userId) {
        List<ShoppingCartEntity> carrito = shoppingCartRepository.findByPurchaseRecordId_User_IdUsuario(userId);

        if (carrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío.");
        }

        for (ShoppingCartEntity item : carrito) {
            ProductoEntity producto = item.getProducto();
            if (producto.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Crear una nueva venta
            VentaEntity venta = new VentaEntity();
            venta.setProducto(producto);
            venta.setUsuario(item.getPurchaseRecordId().getUser());
            venta.setCantidad(item.getQuantity());
            venta.setPrecio(producto.getPrecio());
            venta.setFechaVenta(new Date());
            ventaRepository.save(venta);

            // Actualizar el stock del producto
            producto.setStock(producto.getStock() - item.getQuantity());
            productoRepository.save(producto);

            // Eliminar el item del carrito
            shoppingCartRepository.delete(item);
        }
    }

    @Override
    public ShoppingCartDto createShoppingCart(ShoppingCartDto shoppingCartDto) {
        PurchaseRecordEntity purchaseRecordEntity;
        if (shoppingCartDto.getPurchaseRecordId() <= 0) {
            purchaseRecordEntity = new PurchaseRecordEntity();
            purchaseRecordEntity.setUser(userRepository.findById(shoppingCartDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + shoppingCartDto.getUserId())));
            purchaseRecordEntity.setPurchaseDate(new Date());
            purchaseRecordEntity = purchaseRecordRepository.save(purchaseRecordEntity);
        } else {
            purchaseRecordEntity = purchaseRecordRepository.findById(shoppingCartDto.getPurchaseRecordId())
                    .orElseThrow(() -> new RuntimeException("Registro de compra no encontrado con ID: " + shoppingCartDto.getPurchaseRecordId()));
        }

        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setQuantity(shoppingCartDto.getQuantity());

        ProductoEntity productoEntity = productoRepository.findById(shoppingCartDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + shoppingCartDto.getProductId()));
        shoppingCartEntity.setProducto(productoEntity);

        shoppingCartEntity.setPurchaseRecordId(purchaseRecordEntity);

        ShoppingCartEntity savedEntity = shoppingCartRepository.save(shoppingCartEntity);
        return mapToDto(savedEntity);
    }

    @Override
    public ShoppingCartDto getShoppingCartById(int id) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart no encontrado con ID: " + id));
        return mapToDto(shoppingCartEntity);
    }

    @Override
    public List<ShoppingCartDto> getAllShoppingCarts() {
        List<ShoppingCartEntity> shoppingCartEntities = shoppingCartRepository.findAll();
        return shoppingCartEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteShoppingCartById(int id) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart no encontrado con ID: " + id));
        shoppingCartRepository.delete(shoppingCartEntity);
    }

    @Override
    public List<ShoppingCartDto> getShoppingCartsByUserId(int userId) {
        List<ShoppingCartEntity> shoppingCartEntities = shoppingCartRepository.findByPurchaseRecordId_User_IdUsuario(userId);
        return shoppingCartEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ShoppingCartEntity mapToEntity(ShoppingCartDto shoppingCartDto) {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setId(shoppingCartDto.getId());
        shoppingCartEntity.setQuantity(shoppingCartDto.getQuantity());

        ProductoEntity productoEntity = productoRepository.findById(shoppingCartDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + shoppingCartDto.getProductId()));
        shoppingCartEntity.setProducto(productoEntity);

        PurchaseRecordEntity purchaseRecordEntity = purchaseRecordRepository.findById(shoppingCartDto.getPurchaseRecordId())
                .orElseThrow(() -> new RuntimeException("Registro de compra no encontrado con ID: " + shoppingCartDto.getPurchaseRecordId()));
        shoppingCartEntity.setPurchaseRecordId(purchaseRecordEntity);

        return shoppingCartEntity;
    }

    private ShoppingCartDto mapToDto(ShoppingCartEntity shoppingCartEntity) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(shoppingCartEntity.getId());
        shoppingCartDto.setQuantity(shoppingCartEntity.getQuantity());
        shoppingCartDto.setProductId(shoppingCartEntity.getProducto().getId());
        shoppingCartDto.setProductName(shoppingCartEntity.getProducto().getNombre());
        shoppingCartDto.setProductPrice(shoppingCartEntity.getProducto().getPrecio());
        shoppingCartDto.setPurchaseRecordId(shoppingCartEntity.getPurchaseRecordId().getId());
        return shoppingCartDto;
    }
}