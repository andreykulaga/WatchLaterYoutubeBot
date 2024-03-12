package com.example.watchlateryoutubebot.controllers;

import com.example.watchlateryoutubebot.configurations.GoogleConfig;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;


@RestController
@AllArgsConstructor
public class GoogleAuthCodeController {

    private final GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private final GoogleConfig googleConfig;

    @GetMapping("/googleapiresponse/code")
    public RedirectView onAuthCodeReceived (@RequestParam String code, String scope, String state) throws IOException {
        //TODO add check for scope, it should be equal to requested scope
        System.out.println("code is " + code);
        System.out.println(("user name is " + state));
        GoogleTokenResponse response = authorizationCodeFlow.newTokenRequest(code)
                .setRedirectUri(googleConfig.getRedirectUri())
                .execute();

        authorizationCodeFlow.createAndStoreCredential(response, state);
        return new RedirectView(googleConfig.getRedirectUriAfterTokenReceived());
    }
}
