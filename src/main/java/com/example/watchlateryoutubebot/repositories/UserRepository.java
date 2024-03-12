package com.example.watchlateryoutubebot.repositories;

import com.example.watchlateryoutubebot.models.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<TelegramUser, String> {

}
