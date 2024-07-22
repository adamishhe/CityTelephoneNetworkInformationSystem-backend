package ru.adamishhe.citytelephonenetworkinformationsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Address;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Street;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.AddressRepository;

import java.util.Optional;

@Service
public class AddressService {


    private final AddressRepository addressRepository;


    private final StreetService streetService;

    public AddressService(AddressRepository addressRepository, StreetService streetService) {
        this.addressRepository = addressRepository;
        this.streetService = streetService;
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Page<Address> getAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address addressDetails) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            if (addressDetails.getHouseNumber() != null) {
                address.setHouseNumber(addressDetails.getHouseNumber());
            }
            if (addressDetails.getStreet() != null && addressDetails.getStreet().getId() != null) {
                Street street = streetService.getStreetById(addressDetails.getStreet().getId());
                address.setStreet(street);
            }
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found with id " + id);
        }
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
