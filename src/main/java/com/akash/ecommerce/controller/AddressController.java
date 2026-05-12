package com.akash.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.entity.Address;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.AddressRepository;
import com.akash.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addAddress(
            @RequestBody Address address,
            Authentication authentication
    ) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow();

        address.setUser(user);

        return ResponseEntity.ok(
                addressRepository.save(address)
        );
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(
            Authentication authentication
    ) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                addressRepository.findByUserId(
                        user.getId()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable Long id
    ) {

        addressRepository.deleteById(id);

        return ResponseEntity.ok(
                "Deleted"
        );
    }
}