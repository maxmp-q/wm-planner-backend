package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    private final Firestore db;

    public FirebaseService() {
        this.db = FirestoreClient.getFirestore();
    }

    public String getDB() {
        return db.toString();
    }
}