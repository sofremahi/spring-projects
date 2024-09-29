package com.example.api_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.channels.FileChannel;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class RolesDto {
    private List<String> roles;

}
