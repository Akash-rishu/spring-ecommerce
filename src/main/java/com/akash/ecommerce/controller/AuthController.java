package com.akash.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.ecommerce.dto.AuthRequest;
import com.akash.ecommerce.dto.AuthResponse;
import com.akash.ecommerce.dto.UserDTO;
import com.akash.ecommerce.entity.Role;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.utils.JwtUtil;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // =========================
    // REGISTER
    // =========================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody User user
    ) {

        // EMAIL EXISTS
        if (
                userRepository.existsByEmail(
                        user.getEmail()
                )
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Email already exists"
                    );
        }

        // DEFAULT ROLE
        user.setRole(Role.USER);

        // PASSWORD ENCODE
        user.setPassword(

                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        // SAVE USER
        User savedUser =
                userRepository.save(user);

        // DTO RESPONSE
        UserDTO userDTO =
                new UserDTO(

                        savedUser.getId(),

                        savedUser.getName(),

                        savedUser.getEmail(),

                        savedUser.getRole().name()
                );

        return ResponseEntity.ok(
                userDTO
        );
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(
            @RequestBody AuthRequest request
    ) {

        // AUTHENTICATE
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()
                )
        );

        // FIND USER
        User user =
                userRepository.findByEmail(
                        request.getEmail()
                )

                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        // GENERATE JWT
        String token =
                jwtUtil.generateToken(

                        user.getEmail(),

                        user.getRole().name()
                );

        // RESPONSE
        AuthResponse response =
                new AuthResponse();

        response.setToken(token);

        response.setRole(
                user.getRole().name()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setName(
                user.getName()
        );

        return ResponseEntity.ok(
                response
        );
    }
}