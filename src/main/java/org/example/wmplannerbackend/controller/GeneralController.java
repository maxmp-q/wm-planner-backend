package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.interfaces.HeadingDto;
import org.example.wmplannerbackend.services.GeneralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {
    private final GeneralService generalService;

    public GeneralController(GeneralService generalService){
        this.generalService = generalService;
    }

    @GetMapping("/heading")
    public ResponseEntity<HeadingDto> getHeading(){
        return ResponseEntity.status(200).body(generalService.getHeading());
    }
}
