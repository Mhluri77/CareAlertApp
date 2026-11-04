
package com.carealert.app.controllers;

import com.carealert.app.entities.User;
import com.carealert.app.repositories.UserRepository;
import com.carealert.app.services.PushyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/carealert")
public class PushyTestController {

    private final UserRepository userRepository;
    private final PushyService pushyService;

    public PushyTestController(
            UserRepository userRepository,
            PushyService pushyService
    ) {
        this.userRepository = userRepository;
        this.pushyService = pushyService;
    }

    @PostMapping("/users/{userId}/test-notification")
    public ResponseEntity<Map<String, Object>> sendTestNotification(
            @PathVariable Long userId
    ) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Find the user
            User user = userRepository.findById(userId)
                    .orElse(null);

            if (user == null) {
                response.put("success", false);
                response.put("message", "User not found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response);
            }

            // Get the saved Pushy token
            String token = user.getPushyDeviceToken();

            if (token == null || token.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "No Pushy token registered for this user");

                return ResponseEntity.badRequest().body(response);
            }

            // Send test notification
            String pushyResponse = pushyService.sendNotification(
                    token,
                    "CareAlert Test",
                    "Your CareAlert push notifications are working!"
            );

            response.put("success", true);
            response.put("message", "Test notification submitted to Pushy");
            response.put("pushyResponse", pushyResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("success", false);
            response.put("message", "Failed to send notification");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
