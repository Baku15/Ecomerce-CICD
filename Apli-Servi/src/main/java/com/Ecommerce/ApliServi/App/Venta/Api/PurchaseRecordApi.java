package com.Ecommerce.ApliServi.App.Venta.Api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Venta.Dto.PurchaseRecordDto;
import com.Ecommerce.ApliServi.App.Venta.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Venta.Service.Interface.PurchaseRecordInterface;

@RestController
@RequestMapping("/api/purchase-record")
public class PurchaseRecordApi {
    private final PurchaseRecordInterface purchaseRecordInterface;

    public PurchaseRecordApi(PurchaseRecordInterface purchaseRecordInterface) {
        this.purchaseRecordInterface = purchaseRecordInterface;
    }

    @PostMapping("/create")
    public ResponseEntity<Respuesta> createPurchaseRecord(@RequestBody PurchaseRecordDto purchaseRecordDto) {
        try {
            PurchaseRecordDto createdPurchaseRecord = purchaseRecordInterface.createPurchaseRecord(purchaseRecordDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdPurchaseRecord));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getPurchaseRecordById(@PathVariable int id) {
        try {
            PurchaseRecordDto purchaseRecordDto = purchaseRecordInterface.getPurchaseRecordById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", purchaseRecordDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Purchase Record not found for id: " + id));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllPurchaseRecords() {
        try {
            List<PurchaseRecordDto> purchaseRecords = purchaseRecordInterface.getAllPurchaseRecords();
            return ResponseEntity.ok(new Respuesta("SUCCESS", purchaseRecords));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Respuesta> deletePurchaseRecordById(@PathVariable int id) {
        try {
            purchaseRecordInterface.deletePurchaseRecordById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}
