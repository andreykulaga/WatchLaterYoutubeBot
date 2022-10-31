package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.configurations.GoogleConfig;
import com.example.watchlateryoutubebot.configurations.TelegramConfig;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class test {
    public static void main(String[] args) throws IOException {


        GoogleConfig googleConfig = new GoogleConfig();


    FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File("src/main/resources/credential"));
    GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            googleConfig.getClientId(),
            googleConfig.getClientSecret(),
            googleConfig.getScopeAsArrayAsList())
            .setCredentialDataStore(StoredCredential.getDefaultDataStore(fileDataStoreFactory))
            .setAccessType("offline")
            .build();


        System.out.println(authorizationCodeFlow.loadCredential("379521486").getExpiresInSeconds());
        System.out.println(authorizationCodeFlow.loadCredential("379521486").getExpirationTimeMilliseconds());
        System.out.println(authorizationCodeFlow.loadCredential("379521486").getRefreshToken());
        System.out.println(authorizationCodeFlow.loadCredential("379521486").refreshToken());



    }


}
