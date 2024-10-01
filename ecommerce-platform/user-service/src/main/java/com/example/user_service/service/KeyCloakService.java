package com.example.user_service.service;

import com.example.user_service.entity.User;

import java.security.NoSuchAlgorithmException;

public interface KeyCloakService {
    public void createUserInKeycloak(User user, String temporaryPassword) throws NoSuchAlgorithmException;

}
