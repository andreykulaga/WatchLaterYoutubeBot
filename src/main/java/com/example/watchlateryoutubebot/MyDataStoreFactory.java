package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.models.MyStoredCredential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.caseSensitive;

@AllArgsConstructor
public class MyDataStoreFactory extends AbstractDataStoreFactory {

    @Autowired
    private final CredentialRepository credentialRepository;

    @Override
    protected <V extends Serializable> MyDataStore<V> createDataStore(String id) throws IOException {

        return new MyDataStore<>(this, id, credentialRepository);
    }

    static class MyDataStore<V extends Serializable> implements DataStore<V> {
//    static class MyDataStore<com.example.watchlateryoutubebot.models.MyStoredCredential> implements DataStore<com.example.watchlateryoutubebot.models.MyStoredCredential> {
        private final MyDataStoreFactory myDataStoreFactory;
        String iD;
        private final CredentialRepository credentialRepository;
        private final Lock lock = new ReentrantLock();

        MyDataStore(MyDataStoreFactory myDataStoreFactory, String iD, CredentialRepository credentialRepository) {
            this.myDataStoreFactory = myDataStoreFactory;
            this.iD = iD;
            this.credentialRepository = credentialRepository;
        }



        @Override
        public DataStoreFactory getDataStoreFactory() {
            return myDataStoreFactory;
        }

        @Override
        public String getId() {
            return iD;
        }

        @Override
        public int size() throws IOException {
            return (int)credentialRepository.count();
        }

        @Override
        public boolean isEmpty() throws IOException {
            return (credentialRepository.count() == 0);
        }

        @Override
        public boolean containsKey(String key) throws IOException {
            if (key == null) {
                return false;
            }
            return credentialRepository.existsById(key);
        }

        @Override
        public boolean containsValue(V value) throws IOException {
            if (!value.getClass().getSimpleName().equals("StoredCredential")) {
                return false;
            }
            ExampleMatcher modelMatcher = ExampleMatcher.matching()
                    .withIgnorePaths("telegramId")
                    .withMatcher("storedCredential", caseSensitive());
            MyStoredCredential probe = new MyStoredCredential();
            probe.setStoredCredential((StoredCredential) value);
            Example<MyStoredCredential> example = Example.of(probe, modelMatcher);
            return credentialRepository.exists(example);

//            return credentialRepository.containsValue((StoredCredential) value);
        }

        @Override
        public Set<String> keySet() throws IOException {
            lock.lock();
            try {
                return credentialRepository.getKeySet();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public Collection<V> values() throws IOException {
            lock.lock();
            try {
                List<MyStoredCredential> myStoredCredentialList = credentialRepository.findAll();
                List<V> storedCredentialList = new ArrayList<>();
                for (MyStoredCredential msc: myStoredCredentialList) {
                    byte[] serialized = IOUtils.serialize(msc);
                    storedCredentialList.add(IOUtils.deserialize(serialized));
                }
                return storedCredentialList;
            } finally {
                lock.unlock();
            }
        }



        @Override
        public V get(String key) throws IOException {
            MyStoredCredential msc = credentialRepository.getReferenceById(key);
            byte[] serialized = IOUtils.serialize(msc.getStoredCredential());
            return IOUtils.deserialize(serialized);
        }

        @Override
        public DataStore<V> set(String key, V value) throws IOException {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            Preconditions.checkArgument(value.getClass().getSimpleName().equals("StoredCredential"));
            MyStoredCredential msc = new MyStoredCredential(key, (StoredCredential) value);
            credentialRepository.saveAndFlush(msc);
            return this;
        }

        @Override
        public DataStore<V> clear() throws IOException {
            credentialRepository.deleteAll();
            return this;
        }

        @Override
        public DataStore<V> delete(String key) throws IOException {
            credentialRepository.deleteById(key);
            return this;
        }
    }
}

