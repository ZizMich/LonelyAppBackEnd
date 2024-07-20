package com.aziz.lonelyapp.restapi;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static int counter = 0;

    @GetMapping("/greeting")
    public String greeting() {

        counter++;
        return String.format(template, counter);
    }
}