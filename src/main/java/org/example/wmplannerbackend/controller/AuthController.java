package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
//    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String password){
        boolean login = authService.login(password);
        return ResponseEntity.status(200).body(login);
    }

//    @GetMapping("/test")
//    public ResponseEntity<String> loginTest(){
//        return ResponseEntity.status(200).body(encoder.encode(""));
//    }
}
