package org.example.wmplannerbackend.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(int id) {
      super("User with id: %s already exist!".formatted(id));
    }
}
