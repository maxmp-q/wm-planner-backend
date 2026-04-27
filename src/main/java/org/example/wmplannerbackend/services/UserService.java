package org.example.wmplannerbackend.services;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.wmplannerbackend.exceptions.UserAlreadyExistException;
import org.example.wmplannerbackend.exceptions.UserNotExistException;
import org.example.wmplannerbackend.interfaces.CardDto;
import org.example.wmplannerbackend.interfaces.TimeSlotDto;
import org.example.wmplannerbackend.interfaces.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Firestore db = FirestoreClient.getFirestore();

    private final CardService cardService;
    private final TimeSlotService timeSlotService;

    public UserService(CardService cardService, TimeSlotService timeSlotService){
        this.cardService = cardService;
        this.timeSlotService = timeSlotService;
    }


    public List<UserDto> getAllUsers() {
        try{
            return db.collection("allUsers")
                    .get()
                    .get()
                    .toObjects(UserDto.class);
        } catch(Exception e){
            throw new RuntimeException("Collection allUsers doesn't exist!");
        }
    }

    public UserDto createUser(UserDto user) {
        try{
            DocumentReference docRef = db.collection("allUsers")
                    .document(String.valueOf(user.id));

            DocumentSnapshot snapshot = docRef.get().get();

            if (snapshot.exists()) {
                throw new UserAlreadyExistException(user.id);
            }

            docRef.set(user);
            return user;
        } catch(Exception e){
            throw new RuntimeException("Failed to create user with exception: %s".formatted(e.getMessage()));
        }
    }

    public void deleteUser(int userID) {
        try{
            DocumentReference docRef = db.collection("allUsers")
                    .document(String.valueOf(userID));

            DocumentSnapshot snapshot = docRef.get().get();

            if (snapshot.exists()) {
                docRef.delete();
                this.deleteUserFromCards(userID);
            } else {
                throw new UserNotExistException(userID);
            }
        } catch(Exception e){
            throw new RuntimeException("Failed to delete user with exception: %s".formatted(e.getMessage()));
        }
    }

    private void deleteUserFromCards(int userID){
        List<CardDto> allCards = cardService.getAllCards();

        for(CardDto card: allCards){
            for(TimeSlotDto timeSlot: card.timeSlots){
                timeSlotService.removeUser(card, timeSlot, userID);
            }
        }
    }
}
