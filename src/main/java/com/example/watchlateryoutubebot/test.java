package com.example.watchlateryoutubebot;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class test {
    public static void main(String[] args) throws IOException {

    FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File("src/main/resources/credential"));
    GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            "149242102535-5uo3kngi681dsqp97d6kocntg8esh0ib.apps.googleusercontent.com",
            "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",
            Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl", "https://www.googleapis.com/auth/youtube.readonly"))
            .setCredentialDataStore(StoredCredential.getDefaultDataStore(fileDataStoreFactory))
            .setAccessType("offline")
            .build();


        System.out.println(authorizationCodeFlow.loadCredential("379521486").getExpiresInSeconds());
        System.out.println(authorizationCodeFlow.loadCredential("379521486").getExpirationTimeMilliseconds());
        System.out.println(authorizationCodeFlow.loadCredential("379521486").getRefreshToken());
        System.out.println(authorizationCodeFlow.loadCredential("379521486").refreshToken());



    }


}
