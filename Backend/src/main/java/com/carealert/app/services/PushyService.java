
package com.carealert.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class PushyService {

    @Value("${pushy.secret-api-key}")
    private String secretApiKey;

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

    public PushyService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String sendNotification(
            String deviceToken,
            String title,
            String message
    ) throws Exception {

        // Build notification data for Android
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("message", message);

        // Build Pushy request body
        Map<String, Object> body = new HashMap<>();
        body.put("to", deviceToken);
        body.put("data", data);

        String jsonBody = objectMapper.writeValueAsString(body);

        // Pushy API URL
        String url = "https://api.pushy.me/push?api_key="
                + secretApiKey;

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Send notification
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

            throw new RuntimeException(
                    "Pushy API error: HTTP "
                            + response.statusCode()
                            + " - "
                            + response.body()
            );
        }

        return response.body();
    }
}
