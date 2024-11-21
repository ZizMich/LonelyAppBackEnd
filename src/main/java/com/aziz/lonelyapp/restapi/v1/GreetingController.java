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
        return "hello";
    }

}
