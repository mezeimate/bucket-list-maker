package com.mezeim.bucketlistmaker.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mezeim.bucketlistmaker.BucketListMakerApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Bean
    @Deprecated
    public FirebaseApp createFireBaseApp() throws IOException {

        ClassLoader classLoader = BucketListMakerApplication.class.getClassLoader();
        //File file = new File(Objects.requireNonNull(classLoader.getResource("service/serviceAccountKey.json")).getFile());
        InputStream resourceAsStream = classLoader.getResourceAsStream("service/serviceAccountKey.json");
        if (Objects.isNull(resourceAsStream)) {
            throw new IOException("File not found!");
        }
        //FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(resourceAsStream))
                .build();

        log.info("Firebase config initialized");

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public Firestore createFirestoreClient() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public StorageClient createFirebaseStorage() {
        return StorageClient.getInstance();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public FirebaseAuth createFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public FirebaseMessaging createFirebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}

