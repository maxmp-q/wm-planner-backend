package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.interfaces.CardDto;
import org.example.wmplannerbackend.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardDto> getAll(){
        return cardService.getAllCards();
    }

    @PostMapping
    public ResponseEntity<CardDto> addCard(@RequestBody CardDto card){
        CardDto created = cardService.addCard(card);
        return ResponseEntity.status(201).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardDto> renameCard(
            @PathVariable int id,
            @RequestBody CardDto card
    ){
        CardDto updated = cardService.renameCard(card);
        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable int id){
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
