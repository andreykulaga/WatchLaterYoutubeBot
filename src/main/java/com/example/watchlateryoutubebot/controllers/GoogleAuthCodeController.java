package com.example.watchlateryoutubebot.controllers;

import com.example.watchlateryoutubebot.MyDataStoreFactory;
import com.example.watchlateryoutubebot.configurations.GoogleConfig;
import com.example.watchlateryoutubebot.repositories.UserRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;


@RestController
@AllArgsConstructor
public class GoogleAuthCodeController {

    private final GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private final GoogleConfig googleConfig;
    private final UserRepository userRepository;
    private final MyDataStoreFactory myDataStoreFactory;
    @GetMapping("/googleapiresponse/code")
    public RedirectView onAuthCodeReceived (@RequestParam String code, String scope, String state) throws IOException {

        if (Arrays.stream(scope.split(" ")).anyMatch(t -> t.equalsIgnoreCase(googleConfig.getScopeAsString()))) {
            //TODO add new user to database of users or update in existing user
        }

//        System.out.println("code is " + code);
//        System.out.println(("user name is " + state));
        GoogleTokenResponse response = authorizationCodeFlow.newTokenRequest(code)
                .setRedirectUri(googleConfig.getRedirectUri())
                .execute();

        authorizationCodeFlow.createAndStoreCredential(response, state);
        return new RedirectView(googleConfig.getRedirectUriAfterTokenReceived());
        //todo отправлять сообщение о статусе получения какого-либо scope (не по вебхуку)
    }
}
