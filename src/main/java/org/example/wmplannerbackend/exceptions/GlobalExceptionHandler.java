package org.example.wmplannerbackend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserExist(UserAlreadyExistException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<String> handleUserNotExist(UserNotExistException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(CardAlreadyExistException.class)
    public ResponseEntity<String> handleCardExist(CardAlreadyExistException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(CardNotExistException.class)
    public ResponseEntity<String> handleCardNotExist(CardNotExistException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }
}
