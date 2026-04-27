package org.example.wmplannerbackend.services;

import org.example.wmplannerbackend.interfaces.CardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    public List<CardDto> getAllCards(){
        // TODO: add logic
        return List.of();
    }

    public CardDto addCard(CardDto card){
        // TODO: add logic
        return card;
    }

    public CardDto renameCard(CardDto card){
        // TODO: add logic
        return card;
    }

    public boolean deleteCard(CardDto card){
        // TODO: add logic
        return false;
    }
}
