package com.spring.tour.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {
    @GetMapping("/homepage")
    public String homepage() {
        return "index";
    }
}
