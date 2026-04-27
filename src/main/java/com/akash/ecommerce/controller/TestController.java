package com.akash.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    // Return LIST instead of String
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}