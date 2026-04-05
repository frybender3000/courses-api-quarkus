package com.courses.exception;


import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.Map;



public class ConflictException extends WebApplicationException{

    public ConflictException(String message) {
        super(message, Response.status(Response.Status.CONFLICT)
                .entity(Map.of("message", message))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}