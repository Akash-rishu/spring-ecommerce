package com.akash.ecommerce.serviceimpl;

import com.akash.ecommerce.dto.UserDTO;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Register User
    public Optional<UserDTO> registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return Optional.empty();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

       UserDTO userDTO = new UserDTO(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getRole().name()
);
        return Optional.of(userDTO);
    } 

    // Login User
    public Optional<String> loginUser(String email, String password) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception ex) {
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            String token = jwtUtil.generateToken(userOptional.get().getEmail());
            return Optional.of(token);
        }

        return Optional.empty();
    }
}