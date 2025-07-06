package com.example.Auth_Service.Dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class LoginResDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResDto(String token) {
        this.token = token;
    }
}
