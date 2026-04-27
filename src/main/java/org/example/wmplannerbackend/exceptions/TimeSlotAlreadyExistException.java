package org.example.wmplannerbackend.exceptions;

public class TimeSlotAlreadyExistException extends RuntimeException {
    public TimeSlotAlreadyExistException(int timeID) {
        super("Timeslot with id: %d already exists.".formatted(timeID));
    }
}
