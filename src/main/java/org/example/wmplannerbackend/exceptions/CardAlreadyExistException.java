package org.example.wmplannerbackend.exceptions;

public class CardAlreadyExistException extends RuntimeException {
    public CardAlreadyExistException(int id) {
        super("Card with id: %d already exist.".formatted(id));
    }
}
