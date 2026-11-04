
package com.carealert.app.controllers;

import com.carealert.app.entities.User;
import com.carealert.app.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/carealert")
public class PushyTokenController {

    private final UserRepository userRepository;

    public PushyTokenController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/pushy-token")
    public ResponseEntity<Map<String, Object>> savePushyToken(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        String token = request.get("token");

        if (token == null || token.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Pushy token is required");

            return ResponseEntity.badRequest().body(response);
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        user.setPushyDeviceToken(token);
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Pushy token saved successfully");

        return ResponseEntity.ok(response);
    }
}
