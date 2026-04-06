package com.courses.resource;

import com.courses.dto.LoginRequest;
import com.courses.dto.TokenResponse;
import com.courses.service.AuthService;
import com.courses.repository.UserRepository;

import io.smallrye.jwt.build.Jwt;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @ConfigProperty(name = "jwt.expiration.seconds")
    long expiresIn;

    @POST
    @Path("/token")
    @PermitAll
    public Response token(@Valid LoginRequest request) {
        TokenResponse response = authService.generateToken(request);
        return Response.ok(response).build();



    }


}
