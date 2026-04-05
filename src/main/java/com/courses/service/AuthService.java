package com.courses.service;

import com.courses.dto.LoginRequest;
import com.courses.dto.TokenResponse;
import com.courses.entity.UserEntity;
import com.courses.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    private final UserRepository userRepository;

    @ConfigProperty(name = "jwt.expiration.seconds")
    long expiresIn;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TokenResponse generateToken(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotAuthorizedException("Invalid credentials"));

        if (!user.password.equals(request.password())) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        String token = Jwt.issuer("ada-api")
                .subject(user.email)
                .groups(Set.of(user.role))
                .expiresIn(Duration.ofSeconds(expiresIn))
                .sign();

        return new TokenResponse(token, expiresIn);
    }
}