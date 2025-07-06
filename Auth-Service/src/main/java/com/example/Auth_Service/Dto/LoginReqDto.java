package com.example.Auth_Service.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class LoginReqDto {
    @Email(message = "Email is required")
    @NotBlank(message = "It should not be empty")
    public String email;

    @Size(min = 8, message = "It should be above 8")
    @NotBlank(message = "Password is required")
    public String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
