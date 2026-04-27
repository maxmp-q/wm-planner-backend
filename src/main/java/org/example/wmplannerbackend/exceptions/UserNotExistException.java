package org.example.wmplannerbackend.exceptions;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(int id) {
        super("User with id: %d doesn't exist.".formatted(id));
    }
}
