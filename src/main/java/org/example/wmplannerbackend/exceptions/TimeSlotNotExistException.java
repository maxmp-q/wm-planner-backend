package org.example.wmplannerbackend.exceptions;

public class TimeSlotNotExistException extends RuntimeException {
    public TimeSlotNotExistException(int id) {
        super("Timeslot with the id: %d doesn't exist.".formatted(id));
    }
}
