package com.akash.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.akash.ecommerce.dto.PasswordChangeRequest;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==============================
    // GET PROFILE
    // ==============================
    @GetMapping("/profile")
    public User getProfile() {

        Authentication auth =

                SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email =
                auth.getName();

        return userRepository
                .findByEmail(email)

                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    // ==============================
    // UPDATE PROFILE
    // ==============================
    @PutMapping("/profile")
    public User updateProfile(

            @RequestBody User updatedUser
    ) {

        Authentication auth =

                SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email =
                auth.getName();

        User user =
                userRepository
                .findByEmail(email)

                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        // UPDATE FIELDS
        user.setName(
                updatedUser.getName()
        );

        user.setEmail(
                updatedUser.getEmail()
        );

        user.setPhoneNumber(
                updatedUser.getPhoneNumber()
        );

        return userRepository.save(
                user
        );
    }

    // ==============================
    // CHANGE PASSWORD
    // ==============================
    @PutMapping("/change-password")
    public String changePassword(

            @RequestBody
            PasswordChangeRequest request
    ) {

        Authentication auth =

                SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email =
                auth.getName();

        User user =
                userRepository
                .findByEmail(email)

                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        // CHECK OLD PASSWORD
        if (
            !passwordEncoder.matches(

                    request.getOldPassword(),

                    user.getPassword()
            )
        ) {

            throw new RuntimeException(
                    "Old password incorrect"
            );
        }

        // SET NEW PASSWORD
        user.setPassword(

                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        return "Password Updated Successfully";
    }
}