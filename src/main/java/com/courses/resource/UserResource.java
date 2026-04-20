package com.courses.resource;

import com.courses.service.UserService;
import com.courses.dto.UserCreateRequest;
import com.courses.dto.UserResponse;
import com.courses.entity.UserEntity;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.security.Authenticated;

import java.net.URI;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;

    @POST
    @PermitAll
    public Response create(@Valid UserCreateRequest request, @Context UriInfo uriInfo) {

        UserEntity user = userService.create(request);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.id))
                .build();

        UserResponse response = new UserResponse(
                user.id,
                user.name,
                user.email,
                user.role

        );
        return Response.created(location)
                .entity(response)
                .build();
    }

    @GET
    @Path("/me")
    @Authenticated
    public Response me() {

        String email = jwt.getSubject();

        UserEntity user = userService.findByEmailOrThrow(email);

        UserResponse response = new UserResponse(
                user.id,
                user.name,
                user.email,
                user.role
        );
        return Response.ok(response).build();
    }
}










