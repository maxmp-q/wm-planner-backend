package org.example.wmplannerbackend.services;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.wmplannerbackend.exceptions.UserAlreadyExistException;
import org.example.wmplannerbackend.exceptions.UserNotExistException;
import org.example.wmplannerbackend.interfaces.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Firestore db = FirestoreClient.getFirestore();

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
        } catch(UserAlreadyExistException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException("Failed to create user: %s".formatted(e.getMessage()));
        }
    }

    public void deleteUser(int userID) {
        try{
            DocumentReference docRef = db.collection("allUsers")
                    .document(String.valueOf(userID));

            DocumentSnapshot snapshot = docRef.get().get();

            if (snapshot.exists()) {
                docRef.delete();
            } else {
                throw new UserNotExistException(userID);
            }
        } catch (UserNotExistException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException("Failed to delete user: %s".formatted(e.getMessage()));
        }
    }
}
