package com.example.Auth_Service.service;

import com.example.Auth_Service.Dto.LoginReqDto;
import com.example.Auth_Service.Model.User;
import com.example.Auth_Service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepo userrepo;

    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    private PasswordEncoder passwordencoder;

    public Optional<String> Authenticate(LoginReqDto loginreqdto) {

        Optional<User> ExistingUser = userrepo.findByEmail(loginreqdto.getEmail());
        String ExistingUserPassword = ExistingUser.get().getPassword();
        String role = ExistingUser.get().getRole();

        String loginreqPassword = loginreqdto.getPassword();

        try {
            if (ExistingUser.isPresent() && passwordencoder.matches(loginreqPassword, ExistingUserPassword)) {
                String email = loginreqdto.getEmail();
                String token = jwtutil.generateToken(email,role);
                return Optional.of(token);
            } else {
                new Exception("Email not found");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        try{
            jwtutil.validateToken(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
