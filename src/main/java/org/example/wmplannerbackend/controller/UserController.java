package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.interfaces.UserDto;
import org.example.wmplannerbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public List<UserDto> getAll() {
        return service.getAllUsers();
    }

    @PostMapping()
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        UserDto createdUser =  service.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
