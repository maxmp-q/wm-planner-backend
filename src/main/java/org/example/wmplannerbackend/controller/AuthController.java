package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.interfaces.AuthResponseDto;
import org.example.wmplannerbackend.interfaces.LoginDto;
import org.example.wmplannerbackend.services.AuthService;
import org.example.wmplannerbackend.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
//    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        boolean valid = authService.login(loginDto);

        if (!valid) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String token = jwtService.generateToken();

        return ResponseEntity.status(200).body(new AuthResponseDto(token));
    }

//    @GetMapping("/test")
//    public ResponseEntity<String> loginTest(){
//        return ResponseEntity.status(200).body(encoder.encode(""));
//    }
}
