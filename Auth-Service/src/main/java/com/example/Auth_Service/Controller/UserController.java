package com.example.Auth_Service.Controller;

import com.example.Auth_Service.Dto.LoginReqDto;
import com.example.Auth_Service.Dto.LoginResDto;
import com.example.Auth_Service.Dto.SignupDto;
import com.example.Auth_Service.Repository.UserRepo;
import com.example.Auth_Service.service.AuthService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private AuthService authservice;

    @Autowired
    private UserRepo repo;

    //only for patients
    @PostMapping("/signup")
    public ResponseEntity<String> Signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(authservice.signupuser(signupDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> Login(@RequestBody LoginReqDto loginreqdto) {


        Optional<String> Optionaltoken = authservice.Authenticate(loginreqdto);

        if(Optionaltoken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResDto("","Failed"));
        }
        String token = Optionaltoken.get();
        return ResponseEntity.ok(new LoginResDto(token , "Sucess"));
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token) {
        if( (token == null) || !token.startsWith("Bearer ") ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(authservice.validateToken(token.substring(7))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
