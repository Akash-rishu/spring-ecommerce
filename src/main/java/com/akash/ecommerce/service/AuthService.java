package com.akash.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.utils.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Register
    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword())); // BCrypt
        repo.save(user);
        return "User registered";
    }

    // Login
    public String login(String email, String password) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(email); // JWT
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
