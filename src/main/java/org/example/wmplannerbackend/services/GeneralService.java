package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    private final Firestore db = FirestoreClient.getFirestore();

    public String getHeading(){
        try{
            DocumentSnapshot snapshot = db.collection("config")
                    .document("heading")
                    .get()
                    .get();

            if (!snapshot.exists()) {
                throw new RuntimeException("No heading configured");
            }

            String title = snapshot.getString("title");

            if (title == null) {
                throw new RuntimeException("Title missing");
            }

            return title;
        } catch (Exception e){
            throw new RuntimeException("No Heading found: %s".formatted(e.getMessage()));
        }
    }
}
