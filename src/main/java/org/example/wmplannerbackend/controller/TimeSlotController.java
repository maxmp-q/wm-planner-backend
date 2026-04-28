package org.example.wmplannerbackend.controller;

import org.example.wmplannerbackend.interfaces.TimeSlotDto;
import org.example.wmplannerbackend.services.TimeSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cards/{cardID}/timeslot")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService){
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<TimeSlotDto> addTimeSlot(
            @PathVariable int cardID,
            @RequestBody TimeSlotDto timeSlot
    ){
        TimeSlotDto created = timeSlotService.addTimeSlot(cardID, timeSlot);
        return ResponseEntity.status(201).body(created);
    }

    @PatchMapping
    public ResponseEntity<TimeSlotDto> renameTimeSlot(
            @PathVariable int cardID,
            @RequestParam int timeID,
            @RequestBody TimeSlotDto timeSlot
    ){
        TimeSlotDto updated = timeSlotService.renameTimeSlot(cardID, timeSlot);
        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping
    public ResponseEntity<TimeSlotDto> deleteTimeSlot(
            @PathVariable int cardID,
            @RequestParam int timeID
    ){
        timeSlotService.deleteTimeSlot(cardID, timeID);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/addUser")
    public ResponseEntity<TimeSlotDto> addUserToTimeSlot(
            @PathVariable int cardID,
            @RequestParam int timeID,
            @RequestBody int userID
    ){
        TimeSlotDto updated = timeSlotService.addUser(cardID, timeID, userID);
        return ResponseEntity.status(200).body(updated);
    }

    @PatchMapping("/removeUser")
    public ResponseEntity<TimeSlotDto> removeUserFromTimeSlot(
            @PathVariable int cardID,
            @RequestParam int timeID,
            @RequestBody int userID
    ){
        TimeSlotDto updated = timeSlotService.removeUser(cardID, timeID, userID);
        return ResponseEntity.status(200).body(updated);
    }
}
