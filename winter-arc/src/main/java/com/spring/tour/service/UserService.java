package com.spring.tour.service;

import com.spring.tour.entity.Users;

import java.util.Optional;

public interface UserService {
void addNewUser(Users users);
Optional<Users> findByEmail(String email);

    Object getCurrentUserProfile();
}
