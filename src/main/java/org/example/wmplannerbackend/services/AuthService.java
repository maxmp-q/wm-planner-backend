package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Firestore db = FirestoreClient.getFirestore();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean login(String password) {
        try {
            DocumentSnapshot snapshot = db.collection("config")
                    .document("security")
                    .get()
                    .get();

            if (!snapshot.exists()) {
                throw new RuntimeException("No password configured");
            }

            String storedHash = snapshot.getString("password");

            if (storedHash == null) {
                throw new RuntimeException("Password missing");
            }

            return encoder.matches(password, storedHash);
        } catch (Exception e) {
            throw new RuntimeException("Login failed: %s".formatted(e.getMessage()));
        }
    }
}
