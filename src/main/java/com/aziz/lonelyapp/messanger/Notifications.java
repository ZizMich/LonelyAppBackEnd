package com.aziz.lonelyapp.messanger;

import com.aziz.lonelyapp.model.DeviceTokenEntity;
import com.aziz.lonelyapp.repository.DeviceTokenRepository;
import com.aziz.lonelyapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for sending push notifications to iOS devices.
 * It uses the Apple Push Notification service (APNs) to send notifications to devices registered with the application.
 *
 * @author Aziz
 * @since 1.0
 */
@Component
public class Notifications {

    private static Notifications instance;

    private final DeviceTokenRepository deviceTokenRepository;

    /**
     * Constructor-based dependency injection for the {@link DeviceTokenRepository}.
     *
     * @param deviceTokenRepository the repository for managing device tokens
     */
    @Autowired
    public Notifications(DeviceTokenRepository deviceTokenRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
        instance = this;  // Set the static instance
    }

    /**
     * Returns the singleton instance of the Notifications class.
     *
     * @return the singleton instance
     */
    public static Notifications getInstance() {
        return instance;
    }

    /**
     * Sends a push notification to the iOS devices associated with the given user ID.
     *
     * @param userID the ID of the user to send the notification to
     * @param body the content of the notification
     * @param title the title of the notification
     * @param useSound a flag indicating whether to play a sound with the notification
     * @return true if the notification was sent successfully, false otherwise
     */
    public boolean sendNotification(String userID, String body, String title, Boolean useSound) {
        try {
            List<DeviceTokenEntity> nativeDeviceTokens = deviceTokenRepository.findByUserId(userID);
            if (nativeDeviceTokens.isEmpty()) {
                return false;
            }
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            System.out.println(JWTGenerator.generatateAPNToken());
            String jsonPayload;
            if(useSound) {
                 jsonPayload = String.format("{\"aps\":{\"alert\":{\"title\":\"%s\",\"body\":\"%s\"}, \"sound\":\"default\"}}", title, body);
            }
            else{
                 jsonPayload = String.format("{\"aps\":{\"alert\":{\"title\":\"%s\",\"body\":\"%s\"}}}",title,body);

            }
            for (DeviceTokenEntity token: nativeDeviceTokens) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.push.apple.com:443/3/device/" + token.getDeviceToken()))
                        .timeout(Duration.ofSeconds(10))
                        .header("Authorization", "Bearer " + JWTGenerator.generatateAPNToken())
                        .header("apns-topic", "com.Aziz.LonelyApp")
                        .header("apns-push-type", "alert")
                        .header("apns-priority", "10")
                        .header("apns-expiration", "0")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                // Check the response
                System.out.println("Status code: " + response.statusCode());
                System.out.println("Response body: " + response.body());

            }
            return true;
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
