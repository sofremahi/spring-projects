package com.example.api_gateway.client;

import com.example.api_gateway.dto.RolesDto;
import com.example.api_gateway.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserServiceClient {
    @GetMapping("user/details/{username}")
    ResponseEntity<UserDto> getUserDetails(@PathVariable("username") String username);
    @GetMapping("user/details/{username}/roles")
     ResponseEntity<RolesDto> getUserRoles(@PathVariable("username") String username);
}
