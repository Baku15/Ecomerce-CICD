package com.Ecommerce.ApliServi.App.Usuario_Complemento.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.AddressDto;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Usuario_Complemento.Service.Interface.AddressInterface;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressApi {
    private final AddressInterface addressService;

    @Autowired
    public AddressApi(AddressInterface addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/Address")
    public ResponseEntity<Respuesta> createAddress(@RequestBody AddressDto addressDto) {
        try {
            AddressDto createdAddress = addressService.save(addressDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdAddress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/Address/{id}")
    public ResponseEntity<Respuesta> getAddressById(@PathVariable int id) {
        try {
            AddressDto address = addressService.findById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/Addresses")
    public ResponseEntity<Respuesta> getAllAddresses() {
        try {
            List<AddressDto> addresses = addressService.findAll();
            return ResponseEntity.ok(new Respuesta("SUCCESS", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @PutMapping("/Address/{id}")
    public ResponseEntity<Respuesta> updateAddress(@PathVariable int id, @RequestBody AddressDto addressDto) {
        try {
            addressDto.setAddressId(id); // Setea el ID proporcionado en el DTO
            AddressDto updatedAddress = addressService.update(id, addressDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedAddress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/Address/{id}")
    public ResponseEntity<Respuesta> deleteAddress(@PathVariable int id) {
        try {
            addressService.deleteById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }
}