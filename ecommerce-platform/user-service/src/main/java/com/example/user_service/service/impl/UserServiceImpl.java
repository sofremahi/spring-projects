package com.example.user_service.service.impl;

import com.example.user_service.entity.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.KeyCloakService;
import com.example.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 6;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private KeyCloakService keyCloakService;

    @Override
    public User getUserByUsername(String username) {
        userRepository.findByUsername(username);
        return userRepository.findByUsername(username);
    }

    @Override
    public String registerUser(String username, String password, String email) throws NoSuchAlgorithmException {
        User user = userRepository.findByUsername(username);
        String temporaryPassword = generateTemporaryPassword();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword("{noop}" + temporaryPassword);
        newUser.setEmail(email);
        userRepository.save(newUser);
        keyCloakService.createUserInKeycloak(user, temporaryPassword);
        return temporaryPassword;
    }


    public static String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}