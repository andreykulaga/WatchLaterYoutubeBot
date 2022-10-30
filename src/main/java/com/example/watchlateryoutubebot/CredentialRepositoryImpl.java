package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.models.MyStoredCredential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.caseSensitive;

@Component
public class CredentialRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
//    @Autowired
//    private CredentialRepository credentialRepository;

    @SuppressWarnings("unused")
    public Set<String> getKeySet() {
        String hql = "SELECT telegramId FROM MyStoredCredential";
        TypedQuery<String> query = entityManager.createQuery(hql, String.class);
        List<String> list = query.getResultList();
        return new HashSet<>(list);
    }

//    @SuppressWarnings("unused")
//    public boolean containsValue(StoredCredential value) {
//        ExampleMatcher modelMatcher = ExampleMatcher.matching()
//                .withIgnorePaths("telegramId")
//                .withMatcher("storedCredential", caseSensitive());
//        MyStoredCredential probe = new MyStoredCredential();
//        probe.setStoredCredential(value);
//        Example<MyStoredCredential> example = Example.of(probe, modelMatcher);
//        return credentialRepository.exists(example);
//    }

//    @SuppressWarnings("unused")
//    List<StoredCredential> getValues() {
//        String hql = "SELECT storedCredential FROM MyStoredCredential";
//        TypedQuery<StoredCredential> query = entityManager.createQuery(hql, StoredCredential.class);
//        return query.getResultList();
//    }

}
