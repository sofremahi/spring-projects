package com.spring.tour.service.impl;

import com.spring.tour.entity.Users;
import com.spring.tour.repository.UsersRepo;
import com.spring.tour.utils.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepo.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User not found with username: " + email));
        return new CustomUserDetails(user);
    }
}
