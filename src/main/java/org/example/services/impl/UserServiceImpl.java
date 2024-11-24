package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.BotUser;
import org.example.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository repository;


    public BotUser save(BotUser user) {
        return repository.save(user);
    }


    public BotUser create(BotUser user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }


    public BotUser getByUsername(String username) {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

}