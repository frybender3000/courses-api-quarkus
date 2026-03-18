package com.courses.resource;

import com.courses.dto.CourseCreateRequest;
import com.courses.dto.CourseResponse;
import com.courses.dto.CourseUpdateRequest;
import com.courses.dto.LessonCreateRequest;
import com.courses.dto.LessonResponse;
import com.courses.entity.CourseEntity;
import com.courses.service.CourseService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    private final CourseService service;

    public CourseResource(CourseService service) {
        this.service = service;
    }

    @POST
    public Response create(@Valid CourseCreateRequest request,
                           @Context UriInfo uriInfo) {

        CourseEntity created = service.create(request);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(created.id))
                .build();

        return Response.created(location)
                .entity(service.toResponse(created))
                .build();
    }

    @GET
    public List<CourseResponse> findAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        return service.findAll(page, size);
    }

    @GET
    @Path("/{id}")
    public CourseResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @PUT
    @Path("/{id}")
    public CourseResponse update(@PathParam("id") Long id,
                                 @Valid CourseUpdateRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{courseId}/lessons")
    public Response createLesson(@PathParam("courseId") Long courseId,
                                 @Valid LessonCreateRequest request) {
        return Response.status(Response.Status.CREATED)
                .entity(service.createLesson(courseId, request))
                .build();
    }

    @GET
    @Path("/{courseId}/lessons")
    public List<LessonResponse> listLessons(@PathParam("courseId") Long courseId) {
        return service.listLessons(courseId);
    }
}