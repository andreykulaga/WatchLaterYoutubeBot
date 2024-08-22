package com.example.watchlateryoutubebot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WatchLaterYoutubeBotApplication {

    public static void main(String[] args) {
        //read secrets from AWS Secret Manager
        String jsonSecret = AwsSecretManager.getSecret();
        String[] propertiesToChange = AwsSecretManager.parseJsonSecret(jsonSecret);
        //set secret as the properties
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WatchLaterYoutubeBotApplication.class)
                .properties(propertiesToChange).properties();
        //run the app
        springApplicationBuilder.run(args);
    }

}


