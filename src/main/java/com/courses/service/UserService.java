package com.courses.service;

import com.courses.dto.UserCreateRequest;
import com.courses.entity.UserEntity;
import com.courses.exception.ConflictException;
import com.courses.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public UserEntity create(UserCreateRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflictException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.name = request.name();
        user.email = request.email();
        user.password = request.password();
        user.role = "USER";

        userRepository.persist(user);
        return user;
    }

    public UserEntity findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}