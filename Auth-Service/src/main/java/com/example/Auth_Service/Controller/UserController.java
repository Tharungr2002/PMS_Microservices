package com.example.Auth_Service.Controller;

import com.example.Auth_Service.Dto.LoginReqDto;
import com.example.Auth_Service.Dto.LoginResDto;
import com.example.Auth_Service.Dto.SignupDto;
import com.example.Auth_Service.Repository.UserRepo;
import com.example.Auth_Service.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AuthService authservice;

    @Autowired
    private UserRepo repo;

    @Value("${apikey}")
    private String api_key;

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
    public ResponseEntity<Claims> validateToken(@RequestHeader("Authorization") String token , @RequestHeader("X-Api-key") String apikey) {
        if(!apikey.equals(api_key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if( (token == null) || !token.startsWith("Bearer ") ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Claims claim = authservice.validateToken(token.substring(7));

        return ResponseEntity.ok(claim);

    }

}
