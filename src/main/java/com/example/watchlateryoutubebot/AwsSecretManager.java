package com.example.watchlateryoutubebot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class AwsSecretManager {

    public static String getSecret() {

        String secretName = "WatchLaterYoutubeBot";
        Region region = Region.of("eu-north-1");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }
        return getSecretValueResponse.secretString();
    }

    public static String[] parseJsonSecret(String jsonSecret) {

        ArrayList<String> listOfSecrets = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse JSON string into JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonSecret);

            // Iterate over fields
            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();

            while (fieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> field = fieldsIterator.next();
                String key = field.getKey();
                String value = field.getValue().asText();
                listOfSecrets.add(key+"="+value);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listOfSecrets.toArray(new String[0]);
    }
}
