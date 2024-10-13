package com.spring.tour.controller;


import com.spring.tour.entity.Users;
import com.spring.tour.entity.UsersType;
import com.spring.tour.service.UserService;
import com.spring.tour.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {
    private final UsersTypeService userTypeService;
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> userTypes = userTypeService.findAll();
        model.addAttribute("getAllUserTypes", userTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model) {
        if (userService.findByEmail(users.getEmail()).isPresent()) {
            model.addAttribute("error", "Email is already in use");
            List<UsersType> userTypes = userTypeService.findAll();
            model.addAttribute("getAllUserTypes", userTypes);
            model.addAttribute("user", new Users());
            return "register";
        } else {
            userService.addNewUser(users);
            return "dashboard";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/homepage";
    }
}
