package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.messanger.Notifications;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aziz.lonelyapp.security.JWTGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
/**
 * Handles GET requests to /greeting
 */
@RestController
public class GreetingController {
    @GetMapping("/greeting")
    public String greeting() {
        Notifications notifications = Notifications.getInstance();
        Boolean b = notifications.sendNotification("USER-acf320c8-2b1a-4054-9abd-11be8932a9de");
        return b ? "it works": "fuck it does not work";
    }

}
