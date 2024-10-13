package com.spring.tour.controller;

import com.spring.tour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {
    private final UserService userService;

    @GetMapping("/dashboard/")
    public String dashboard(Model model) {
        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(currentUserProfile instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("username", authentication.getName());
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }
}
