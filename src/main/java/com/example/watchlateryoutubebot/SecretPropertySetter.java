package com.example.watchlateryoutubebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SecretPropertySetter {

    public static String[] readSecretFromFile() {
        String secretFilePath = "my_secrets";
        ArrayList<String> secrets = new ArrayList<>();
        String[] result = new String[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(secretFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                secrets.add(line);
            }
            return secrets.toArray(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
