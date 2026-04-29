package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.wmplannerbackend.interfaces.HeadingDto;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    private final Firestore db = FirestoreClient.getFirestore();

    public HeadingDto getHeading(){
        try{
            DocumentSnapshot snapshot = db.collection("config")
                    .document("heading")
                    .get()
                    .get();

            if (!snapshot.exists()) {
                throw new RuntimeException("No heading configured");
            }

            HeadingDto heading = snapshot.toObject(HeadingDto.class);

            if (heading == null || heading.title == null) {
                throw new RuntimeException("Title missing");
            }

            return heading;
        } catch (Exception e){
            throw new RuntimeException("No Heading found: %s".formatted(e.getMessage()));
        }
    }
}
