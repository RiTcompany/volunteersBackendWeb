package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.User;
import org.example.enums.ERole;
import org.example.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository repository;


    public User save(User user) {
        return repository.save(user);
    }


    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }


    public User getByUsername(String username) {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

}