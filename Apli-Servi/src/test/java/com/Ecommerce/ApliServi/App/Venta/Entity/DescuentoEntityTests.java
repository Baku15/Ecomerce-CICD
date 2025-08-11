package com.Ecommerce.ApliServi.App.Venta.Entity;

import com.Ecommerce.ApliServi.App.Producto.Entity.DescuentoEntity;
import com.Ecommerce.ApliServi.App.Producto.Repository.DescuentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class DescuentoEntityTests {

    @Autowired
    private DescuentoRepository descuentoRepository;

    @Test
    public void testCreateAndFindDescuento() {
        DescuentoEntity descuento = new DescuentoEntity();
        descuento.setPercentage(10);
        descuentoRepository.save(descuento);

        List<DescuentoEntity> found = descuentoRepository.findAll();
        assertFalse(found.isEmpty());
        assertEquals(10, found.get(0).getPercentage());
    }
}
