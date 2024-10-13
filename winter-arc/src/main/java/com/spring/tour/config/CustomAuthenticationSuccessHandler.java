package com.spring.tour.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String userName = user.getUsername();
        System.out.println("the user with email: "+userName+" has logged in");
        boolean recruiter = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("recruiter"));
        boolean jobSeeker = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("job seeker"));
        if (recruiter||jobSeeker) {
            response.sendRedirect("/dashboard/");
        }
    }
}
