package com.example.watchlateryoutubebot.models;

import com.google.api.client.auth.oauth2.StoredCredential;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MyStoredCredential implements Serializable {
    @Id
    private String telegramId;
    private StoredCredential storedCredential;
}
