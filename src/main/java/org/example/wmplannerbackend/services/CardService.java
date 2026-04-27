package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.wmplannerbackend.exceptions.CardAlreadyExistException;
import org.example.wmplannerbackend.exceptions.CardNotExistException;
import org.example.wmplannerbackend.interfaces.CardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final Firestore db = FirestoreClient.getFirestore();

    public List<CardDto> getAllCards(){
        try{
            return db.collection("cards")
                    .get()
                    .get()
                    .toObjects(CardDto.class);
        } catch (Exception e){
            throw new RuntimeException("Collection cards doesn't exist." + e.getMessage());
        }
    }

    public CardDto addCard(CardDto card){
        try{
            DocumentReference docRef = db.collection("cards")
                    .document(String.valueOf(card.id));

            DocumentSnapshot snapshot = docRef.get().get();

            if (snapshot.exists()) {
                throw new CardAlreadyExistException(card.id);
            }

            docRef.set(card).get();
            return card;
        } catch(CardAlreadyExistException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(("Failed to create card: %s").formatted(e.getMessage()));
        }
    }

    public CardDto renameCard(CardDto card){
        try{
            DocumentReference docRef = db.collection("cards")
                    .document(String.valueOf(card.id));

            DocumentSnapshot snapshot = docRef.get().get();

            if (!snapshot.exists()) {
                throw new CardNotExistException(card.id);
            }

            docRef.update("title", card.title).get();
            return card;
        } catch(CardNotExistException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException("Failed to rename card: %s".formatted(e.getMessage()));
        }
    }

    public void deleteCard(int cardID){
        try{
            DocumentReference docRef = db.collection("cards")
                    .document(String.valueOf(cardID));

            DocumentSnapshot snapshot = docRef.get().get();

            if (!snapshot.exists()) {
                throw new CardNotExistException(cardID);
            }

            docRef.delete().get();
        } catch(CardNotExistException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException("Failed to delete card: %s".formatted(e.getMessage()));
        }
    }
}
