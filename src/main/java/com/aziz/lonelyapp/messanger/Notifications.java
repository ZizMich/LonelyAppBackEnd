package com.aziz.lonelyapp.messanger;

import com.aziz.lonelyapp.model.DeviceTokenEntity;
import com.aziz.lonelyapp.repository.DeviceTokenRepository;
import com.aziz.lonelyapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

// Annotate the class with @Component to allow Spring to manage it
@Component
public class Notifications {

    private static Notifications instance;

    private final DeviceTokenRepository deviceTokenRepository;

    // Constructor-based dependency injection
    @Autowired
    public Notifications(DeviceTokenRepository deviceTokenRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
        instance = this;  // Set the static instance
    }
    public static Notifications getInstance() {
            return instance;
        }

    public boolean sendNotification(String userID) {
        try {
            List<DeviceTokenEntity> nativeDeviceTokens = deviceTokenRepository.findByUserId(userID);
            if (nativeDeviceTokens.isEmpty()) {
                return false;
            }
            for (DeviceTokenEntity token: nativeDeviceTokens) {


            URL url = new URL("https://api.sandbox.push.apple.com/3/device/" + token.getDeviceToken());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("apns-topic", "com.anonymous.LonelyApp");
            connection.setRequestProperty("authorization", "bearer " + JWTGenerator.generatateAPNToken());
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            String jsonPayload = "{\n" +
                    "    aps: {\n" +
                    "      alert: {\n" +
                    "        title: \"\uD83D\uDCE7 You've got mail!\",\n" +
                    "        body: 'Hello world! \uD83C\uDF10',\n" +
                    "      },\n" +
                    "    },\n" +
                    "    experienceId: '@yourExpoUsername/yourProjectSlug', // Required when testing in the Expo Go app\n" +
                    "    scopeKey: '@yourExpoUsername/yourProjectSlug', // Required when testing in the Expo Go app\n" +
                    "  }";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            System.out.println(connection.getContent());
            }
            return true;
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
