package com.Ecommerce.ApliServi.App.Venta.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Venta.Dto.PurchaseRecordDto;

public interface PurchaseRecordInterface {
    PurchaseRecordDto createPurchaseRecord(PurchaseRecordDto purchaseRecordDto);

    PurchaseRecordDto getPurchaseRecordById(int id);

    List<PurchaseRecordDto> getAllPurchaseRecords();

    void deletePurchaseRecordById(int id);
}
