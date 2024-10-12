package com.spring.tour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {
    @GetMapping("/homepage")
    public String homepage() {
        return "Hello World";
    }

}
