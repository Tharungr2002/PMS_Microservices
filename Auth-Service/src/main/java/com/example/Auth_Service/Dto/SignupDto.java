package com.example.Auth_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupDto {

    private String username;

    private String email;

    private String password;

    private String role;
}
