package com.example.user_service.service.impl;

import com.example.user_service.entity.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    @Override
    public User GetUserByUsername(String username) {
        userRepository.findByUsername(username);
        return userRepository.findByUsername(username);
    }
}