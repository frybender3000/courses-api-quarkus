package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/hello")
public class ExampleResource {

    @GET
    public String hello() {
        return "Hello from Quarkus REST";
    }
}