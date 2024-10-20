package com.aziz.lonelyapp.restapi.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aziz.lonelyapp.security.JWTGenerator;
/**
 * Handles GET requests to /greeting
 */
@RestController
public class GreetingController {

    /**
     * The greeting template
     */
    private static final String template = "Hello, %s!";

    /**
     * The number of times the greeting was requested
     */
    private static int counter = 0;

    /**
     * Handles GET requests to /greeting
     * 
     * @return The greeting
     */
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello";
        // Increment the counter

    }

}
