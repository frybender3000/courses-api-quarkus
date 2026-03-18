package com.courses.exception;

import com.courses.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .toList();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Validation error", errors))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
