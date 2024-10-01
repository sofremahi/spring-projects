package com.example.user_service.service;

import com.example.user_service.entity.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    User getUserByUsername(String username);
    String registerUser(String username , String password , String email) throws NoSuchAlgorithmException;
}
