package com.example.Auth_Service.service;

import com.example.Auth_Service.Dto.LoginReqDto;
import com.example.Auth_Service.Dto.SignupDto;
import com.example.Auth_Service.Enum.role;
import com.example.Auth_Service.Model.User;
import com.example.Auth_Service.Repository.UserRepo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        String reqemail = loginreqdto.getEmail();
        System.out.println(userrepo.findAll());
        Optional<User> ExistingUser = userrepo.findByEmail(reqemail);
        System.out.println(ExistingUser);

        String loginreqPassword = loginreqdto.getPassword();

        try {
            if (ExistingUser.isPresent()) {
                String ExistingUserPassword = ExistingUser.get().getPassword();
                String role = ExistingUser.get().getUserRole().toString();
                if (passwordencoder.matches(loginreqPassword, ExistingUserPassword)) {
                    String email = loginreqdto.getEmail();
                    String token = jwtutil.generateToken(email, role);
                    return Optional.of(token);
                }
            }else {
                new Exception("Email not found");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Claims validateToken(String token) {
        try{
            Claims claims = jwtutil.validateToken(token);
            return claims;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String signupuser(SignupDto signupDto) {
        try {
            if(userrepo.existsByEmail(signupDto.getEmail())) {
                throw new RuntimeException("Email already exists!!");
            }
            String encodedPassword = passwordencoder.encode(signupDto.getPassword());
            User user = new User();
            user.setEmail(signupDto.getEmail());
            user.setPassword(encodedPassword);

            role userRole = role.valueOf(signupDto.getRole().toUpperCase());
            user.setUserRole(userRole);
            userrepo.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "Profile created Successfully";
    }
}
