package com.carealert.app.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.carealert.app.entities.User;
import com.carealert.app.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Long id;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {

        user.setPasswordHash(
                passwordEncoder.encode(
                        user.getPasswordHash()
                )
        );

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return List.of();
    }

    public User getUserById(Long id) {
        this.id = id;
        return null;
    }

    public void deleteUser(Long id) {
    }

    // Keep the rest unchanged
}