package com.example.watchlateryoutubebot.configurations;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

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


}
