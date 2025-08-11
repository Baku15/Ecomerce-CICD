package com.Ecommerce.ApliServi.App.Venta.Entity;

import com.Ecommerce.ApliServi.App.Producto.Entity.DescuentoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.DescuentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DescuentoEntityTests {

    @Autowired
    private DescuentoRepository descuentoRepository;

    @Test
    public void testCreateAndFindDescuento() {
        descuentoRepository.deleteAll();  // limpiar antes de test

        DescuentoEntity descuento = new DescuentoEntity();
        descuento.setPercentage(new BigDecimal("10"));
        descuentoRepository.save(descuento);

        List<DescuentoEntity> found = descuentoRepository.findAll();
        assertFalse(found.isEmpty());

        System.out.println("Found percentage: " + found.get(0).getPercentage());

        assertTrue(found.get(0).getPercentage().compareTo(new BigDecimal("10")) == 0);
    }
}