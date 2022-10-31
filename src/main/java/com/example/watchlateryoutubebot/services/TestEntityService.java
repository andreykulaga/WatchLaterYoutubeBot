package com.example.watchlateryoutubebot.services;

import com.example.watchlateryoutubebot.models.TestEntity;
import com.example.watchlateryoutubebot.repositories.TestEntityRepository;

public class TestEntityService {
    private final TestEntityRepository testEntityRepository;

    public TestEntityService(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
    }

    public TestEntity getEntity(Long id) {
        return this.testEntityRepository.getReferenceById(id);
    }

    public TestEntity putEntity(String name,
                                Integer intValue) {
        return this.testEntityRepository.save(new TestEntity(name, intValue));
    }
}
