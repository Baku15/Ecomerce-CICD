package com.Ecommerce.ApliServi.App.Venta.Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Venta.Dto.PurchaseRecordDto;
import com.Ecommerce.ApliServi.App.Venta.Entity.PurchaseRecordEntity;
import com.Ecommerce.ApliServi.App.Venta.Repository.PurchaseRecordRepository;
import com.Ecommerce.ApliServi.App.Venta.Service.Interface.PurchaseRecordInterface;
@Service
public class PurchaseRecordServicios implements PurchaseRecordInterface {
    private final PurchaseRecordRepository purchaseRecordRepository;
    private final UserRepository userRepository;

    @Autowired
    public PurchaseRecordServicios(PurchaseRecordRepository purchaseRecordRepository, UserRepository userRepository) {
        this.purchaseRecordRepository = purchaseRecordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PurchaseRecordDto createPurchaseRecord(PurchaseRecordDto purchaseRecordDto) {
        PurchaseRecordEntity purchaseRecordEntity = mapToEntity(purchaseRecordDto);
        PurchaseRecordEntity savedEntity = purchaseRecordRepository.save(purchaseRecordEntity);
        return mapToDto(savedEntity);
    }

    @Override
    public PurchaseRecordDto getPurchaseRecordById(int id) {
        PurchaseRecordEntity purchaseRecordEntity = purchaseRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Record not found with ID: " + id));
        return mapToDto(purchaseRecordEntity);
    }

    @Override
    public List<PurchaseRecordDto> getAllPurchaseRecords() {
        List<PurchaseRecordEntity> purchaseRecordEntities = purchaseRecordRepository.findAll();
        return purchaseRecordEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePurchaseRecordById(int id) {
        PurchaseRecordEntity purchaseRecordEntity = purchaseRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Record not found with ID: " + id));
        purchaseRecordRepository.delete(purchaseRecordEntity);
    }

    private PurchaseRecordEntity mapToEntity(PurchaseRecordDto purchaseRecordDto) {
        PurchaseRecordEntity purchaseRecordEntity = new PurchaseRecordEntity();
        purchaseRecordEntity.setId(purchaseRecordDto.getId());
        purchaseRecordEntity.setPurchaseDate(purchaseRecordDto.getPurchaseDate());
        UserEntity usuarioEntity = userRepository.findById(purchaseRecordDto.getUserId())
                .orElseThrow(
                        () -> new RuntimeException("El usuario con ID " + purchaseRecordDto.getUserId() + " no existe."));
        purchaseRecordEntity.setUser(usuarioEntity);
        return purchaseRecordEntity;
    }

    private PurchaseRecordDto mapToDto(PurchaseRecordEntity purchaseRecordEntity) {
        PurchaseRecordDto purchaseRecordDto = new PurchaseRecordDto();
        purchaseRecordDto.setId(purchaseRecordEntity.getId());
        purchaseRecordDto.setPurchaseDate(purchaseRecordEntity.getPurchaseDate());
        purchaseRecordDto.setUserId(purchaseRecordEntity.getUser().getIdUsuario());
        return purchaseRecordDto;
    }
}
