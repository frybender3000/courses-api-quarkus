package com.courses.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ContentTypeFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext ctx) {

        if ("POST".equals(ctx.getMethod()) || "PUT".equals(ctx.getMethod())) {

            String contentType = ctx.getHeaderString("Content-Type");

            if (contentType == null || !contentType.contains("application/json")) {
                ctx.abortWith(
                        Response.status(415)
                                .entity("{\"message\":\"Unsupported Media Type\"}")
                                .type(MediaType.APPLICATION_JSON)
                                .build()
                );
            }
        }
    }
}

