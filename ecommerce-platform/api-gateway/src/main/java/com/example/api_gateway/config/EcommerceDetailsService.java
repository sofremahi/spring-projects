package com.example.api_gateway.config;

import com.example.api_gateway.client.UserServiceClient;
import com.example.api_gateway.dto.RolesDto;
import com.example.api_gateway.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EcommerceDetailsService implements UserDetailsService {
    UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto customer = userServiceClient.getUserDetails(username).getBody();
        RolesDto roles = userServiceClient.getUserRoles(username).getBody();
        if (customer == null || roles == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = roles.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(customer.getUsername(), customer.getPassword(), authorities);
    }
}
