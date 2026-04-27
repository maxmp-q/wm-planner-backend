package org.example.wmplannerbackend.services;

import org.example.wmplannerbackend.interfaces.CardDto;
import org.example.wmplannerbackend.interfaces.TimeSlotDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCleanupService {

    private final CardService cardService;
    private final TimeSlotService timeSlotService;

    public UserCleanupService(CardService cardService, TimeSlotService timeSlotService) {
        this.cardService = cardService;
        this.timeSlotService = timeSlotService;
    }

    public void removeUserFromAllCards(int userID) {
        List<CardDto> allCards = cardService.getAllCards();

        for (CardDto card : allCards) {
            for (TimeSlotDto timeSlot : card.timeSlots) {
                timeSlotService.removeUser(card.id, timeSlot.id, userID);
            }
        }
    }
}
