package org.example.wmplannerbackend.exceptions;

public class CardNotExistException extends RuntimeException {
    public CardNotExistException(int id) {
        super("Card with id: %d doesn't exist.".formatted(id));
    }
}
