package com.example.demo.request_response;

import com.example.demo.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private  String jwt;
    private String userName;
    private Role role;

}
