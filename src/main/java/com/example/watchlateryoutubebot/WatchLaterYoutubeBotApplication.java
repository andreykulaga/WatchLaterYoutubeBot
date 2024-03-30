package com.example.watchlateryoutubebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

@SpringBootApplication
public class WatchLaterYoutubeBotApplication {

    public static void main(String[] args) {
        String[] propertiesToChange = SecretPropertySetter.readSecretFromFile();
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WatchLaterYoutubeBotApplication.class)
                .properties(propertiesToChange).properties();
        springApplicationBuilder.run(args);

    }

}


