package com.example.watchlateryoutubebot.configurations;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoogleConfig {
    @Value("${google.clientId}")
    String clientId;
    @Value("${google.clientSecret}")
    String clientSecret;
    @Value("${google.redirectionUri}")
    String redirectUri;
    @Value("${google.redirectUriAfterTokenReceived}")
    String redirectUriAfterTokenReceived;
    @Value("${google.scopes}")
    String scopeAsString;

    public List<String> getScopeAsArrayAsList() {
        return Arrays.asList(scopeAsString.split(" "));
    }




}
