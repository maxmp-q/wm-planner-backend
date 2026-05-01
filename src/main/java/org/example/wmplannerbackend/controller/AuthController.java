package org.example.wmplannerbackend.controller;

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
    public ResponseEntity<Boolean> login(@RequestBody LoginDto loginDto){
        boolean login = authService.login(loginDto);
        return ResponseEntity.status(200).body(login);
    }

//    @GetMapping("/test")
//    public ResponseEntity<String> loginTest(){
//        return ResponseEntity.status(200).body(encoder.encode(""));
//    }
}
