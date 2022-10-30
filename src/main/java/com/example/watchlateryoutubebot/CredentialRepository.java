package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.models.MyStoredCredential;
import com.google.api.client.auth.oauth2.StoredCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface CredentialRepository extends JpaRepository<MyStoredCredential, String> {
    Set<String> getKeySet();
//    Boolean containsValue(StoredCredential value);
//    List<StoredCredential> getValues();

}
