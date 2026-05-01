package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.wmplannerbackend.interfaces.LoginDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Firestore db = FirestoreClient.getFirestore();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean login(LoginDto login) {
        try {
            DocumentSnapshot snapshot = db.collection("config")
                    .document("security")
                    .get()
                    .get();

            if (!snapshot.exists()) {
                throw new RuntimeException("No username and password configured");
            }

            String storedHash = snapshot.getString("password");
            String storedUser = snapshot.getString("user");

            if (storedHash == null || storedUser == null) {
                throw new RuntimeException("Username or Password missing");
            }

            return storedUser.matches(login.username) && encoder.matches(login.password, storedHash);
        } catch (Exception e) {
            throw new RuntimeException("Login failed: %s".formatted(e.getMessage()));
        }
    }
}
