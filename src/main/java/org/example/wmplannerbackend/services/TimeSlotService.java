package org.example.wmplannerbackend.services;

import org.example.wmplannerbackend.interfaces.CardDto;
import org.example.wmplannerbackend.interfaces.TimeSlotDto;
import org.example.wmplannerbackend.interfaces.UserDto;
import org.springframework.stereotype.Service;


@Service
public class TimeSlotService {

    public TimeSlotDto addTimeSlot(CardDto card, TimeSlotDto timeSlot){
        // TODO: add logic
        return timeSlot;
    }

    public TimeSlotDto renameTimeSlot(CardDto card, TimeSlotDto timeSlot){
        // TODO: add logic
        return timeSlot;
    }

    public boolean deleteTimeSlot(CardDto card, TimeSlotDto timeSlot){
        // TODO: add logic
        return false;
    }

    public TimeSlotDto addUser(CardDto card, TimeSlotDto timeSlot, UserDto user){
        // TODO: add logic
        return timeSlot;
    }

    public TimeSlotDto removeUser(CardDto card, TimeSlotDto timeSlot, int userID){
        // TODO: add logic
        return timeSlot;
    }
}
