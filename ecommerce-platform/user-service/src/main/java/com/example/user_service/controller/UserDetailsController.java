package com.example.user_service.controller;

import com.example.user_service.dto.RolesDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.User;
import com.example.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserDetailsController {
    private UserService userService;

    @GetMapping("/details/{username}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("username") String username) {
       User user = userService.getUserByUsername(username);
       UserDto userDto = new UserDto(user.getUsername(), user.getPassword() , user.getEmail());
       return ResponseEntity.ok(userDto);
    }
    @GetMapping("/details/{username}/roles")
    public ResponseEntity<RolesDto> getUserRoles(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        RolesDto rolesDto = new RolesDto(user.getRoles().stream().map(Role::getName).toList());
        return ResponseEntity.ok(rolesDto);
    }
    @PostMapping("/register/user")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        String tempPass = userService.registerUser(userDto.getUsername() , userDto.getPassword() , userDto.getEmail());
        return ResponseEntity.ok("User registered successfully" +
                "your temp generated password is "+ tempPass);
    }
}
