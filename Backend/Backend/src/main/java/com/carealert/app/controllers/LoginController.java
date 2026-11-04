package com.carealert.app.controllers;

import com.carealert.app.dto.LoginRequest;
import com.carealert.app.entities.User;
import com.carealert.app.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest request
    ) {

        Map<String, Object> response =
                new HashMap<>();

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                );

        if (user == null) {

            response.put(
                    "success",
                    false
            );

            response.put(
                    "message",
                    "User not found"
            );

            return ResponseEntity
                    .status(
                            HttpStatus.UNAUTHORIZED
                    )
                    .body(response);
        }

        boolean validPassword =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                );

        if (validPassword) {

            response.put(
                    "success",
                    true
            );

            response.put(
                    "message",
                    "Login successful"
            );

            response.put(
                    "userId",
                    user.getUserId()
            );

            response.put(
                    "email",
                    user.getEmail()
            );

            response.put(
                    "fullName",
                    user.getFullName()
            );

            return ResponseEntity.ok(response);
        }

        response.put(
                "success",
                false
        );

        response.put(
                "message",
                "Invalid credentials"
        );

        return ResponseEntity
                .status(
                        HttpStatus.UNAUTHORIZED
                )
                .body(response);
    }
}
