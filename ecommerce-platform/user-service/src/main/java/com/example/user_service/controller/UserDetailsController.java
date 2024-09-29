package com.example.user_service.controller;

import com.example.user_service.dto.RolesDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserDetailsController {
    private UserService userService;

    @GetMapping("/details/{username}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("username") String username) {
       User user = userService.GetUserByUsername(username);
       UserDto userDto = new UserDto(user.getUsername(), user.getPassword());
       return ResponseEntity.ok(userDto);
    }
    @GetMapping("/details/{username}/roles")
    public ResponseEntity<RolesDto> getUserRoles(@PathVariable("username") String username) {
        User user = userService.GetUserByUsername(username);
        RolesDto rolesDto = new RolesDto(user.getRoles().stream().map(Role::getName).toList());
        return ResponseEntity.ok(rolesDto);
    }
}
