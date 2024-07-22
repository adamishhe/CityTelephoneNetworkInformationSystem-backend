package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Address;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Street;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.AddressService;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.StreetService;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {


    private final AddressService addressService;


    private final StreetService streetService;

    public AddressController(AddressService addressService, StreetService streetService) {
        this.addressService = addressService;
        this.streetService = streetService;
    }

    @GetMapping
    public Page<Address> getAllAddresses(Pageable pageable) {
        return addressService.getAllAddresses(pageable);
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        Street street = streetService.getStreetById(address.getStreet().getId());
        address.setStreet(street);
        return addressService.createAddress(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        if (addressDetails.getStreet() != null && addressDetails.getStreet().getId() != null) {
            Street street = streetService.getStreetById(addressDetails.getStreet().getId());
            addressDetails.setStreet(street);
        }
        Address updatedAddress = addressService.updateAddress(id, addressDetails);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }
}
