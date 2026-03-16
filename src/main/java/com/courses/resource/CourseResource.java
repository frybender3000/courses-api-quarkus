package com.courses.resource;

import com.courses.dto.CourseCreateRequest;
import com.courses.dto.CourseResponse;
import com.courses.dto.CourseUpdateRequest;
import com.courses.entity.CourseEntity;
import com.courses.service.CourseService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    private final CourseService service;

    public CourseResource(CourseService service) {
        this.service = service;
    }

    @GET
    public Response listAll() {
        return Response.ok(service.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        CourseEntity course = service.findById(id);

        if (course == null) {
            throw new NotFoundException("course not found");
        }

        return Response.ok(new CourseResponse(course.id, course.name)).build();
    }

    @POST
    public Response create(@Valid CourseCreateRequest request, @Context UriInfo uriInfo) {
        CourseEntity created = service.create(request);

        return Response.created(
                        uriInfo.getAbsolutePathBuilder()
                                .path(String.valueOf(created.id))
                                .build()
                )
                .entity(new CourseResponse(created.id, created.name))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,
                           @Valid CourseUpdateRequest request) {

        CourseEntity updated = service.update(id, request);

        if (updated == null) {
            throw new NotFoundException("course not found");
        }

        return Response.ok(new CourseResponse(updated.id, updated.name)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);

        if (!deleted) {
            throw new NotFoundException("course not found");
        }

        return Response.noContent().build();
    }
}