package org.example.repositories;

import org.example.entities.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findByEmail(String username);

    boolean existsByEmail(String email);
}
