package org.example.wmplannerbackend.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            String firebaseConfig = System.getenv("FIREBASE_CONFIG");

            if(firebaseConfig == null || firebaseConfig.isEmpty()){
                throw new IllegalStateException("FIREBASE_CONFIG is not set");
            }


            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(
                                    new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8))
                            )
                    )
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}