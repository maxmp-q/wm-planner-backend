package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.interfaces.UserDto;
import org.example.wmplannerbackend.services.UserCleanupService;
import org.example.wmplannerbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final UserCleanupService cleanupService;

    public UserController(UserService service, UserCleanupService cleanupService) {
        this.service = service;
        this.cleanupService = cleanupService;
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

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id){
        service.deleteUser(id);
        cleanupService.removeUserFromAllCards(id);
        return ResponseEntity.noContent().build();
    }
}
