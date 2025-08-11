package com.Ecommerce.ApliServi.App.Producto.Service.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Ecommerce.ApliServi.App.Producto.Entity.DescuentoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.DescuentoRepository;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;

@Component
public class DiscountInitializer implements CommandLineRunner {
    private final DescuentoRepository discountRepository;

    @Autowired
    public DiscountInitializer(DescuentoRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (discountRepository.count() == 0) {
            // Cantidad total deseada de descuentos
            BigDecimal totalDiscount = BigDecimal.valueOf(1);

            // Descuento inicial por iteración
            BigDecimal discountPerIteration = BigDecimal.valueOf(0.01);

            // Escala y redondeo para manejar precisión
            int scale = 2;

            // Iterar desde 0.01 hasta 1.00
            for (BigDecimal i = discountPerIteration; i.compareTo(totalDiscount) <= 0; i = i.add(discountPerIteration)
                    .setScale(scale, RoundingMode.HALF_EVEN)) {
                // Crear un nuevo descuento
                DescuentoEntity discountEntity = new DescuentoEntity();
                discountEntity.setPercentage(i);  // <-- Aquí solo paso i sin convertir

                // Guardar el descuento en la base de datos
                discountRepository.save(discountEntity);
            }
        }
    }
}
