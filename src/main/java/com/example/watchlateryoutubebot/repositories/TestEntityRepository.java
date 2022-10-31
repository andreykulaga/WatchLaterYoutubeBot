package com.example.watchlateryoutubebot.repositories;

import com.example.watchlateryoutubebot.models.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}
