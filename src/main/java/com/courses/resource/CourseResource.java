package com.courses.resource;

import com.courses.dto.CourseCreateRequest;
import com.courses.dto.CourseResponse;
import com.courses.dto.CourseUpdateRequest;
import com.courses.dto.LessonResponse;
import com.courses.entity.CourseEntity;
import com.courses.service.CourseService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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

    @Inject
    CourseService courseService;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid CourseCreateRequest request, @Context UriInfo uriInfo) {
        CourseResponse response = courseService.create(request);


        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(response.id()))
                .build();

        return Response.created(location)
                .entity(response)
                .build();


    }


    @GET
    @PermitAll
    public Response list( @QueryParam("page") @DefaultValue("0") int page,
                          @QueryParam("size") @DefaultValue("10") int size
    ) {
        List<CourseResponse> response = courseService.findAll(page, size);
        return Response.ok(response).build();
    }
    @GET
    @Path("/{id}")
    @PermitAll
    public Response findById(@PathParam("id") Long id) {
        CourseResponse response = courseService.findById(id);

        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid CourseUpdateRequest request) {
        CourseResponse response = courseService.update(id, request);
        return Response.ok(response).build();

    }
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        courseService.delete(id);
        return Response.noContent().build();
    }

    private CourseResponse toResponse(CourseEntity entity) {
        List<LessonResponse> lessons = entity.lessons == null ? List.of() : entity.lessons.stream()
                .map(lesson -> new LessonResponse(lesson.id, lesson.name))
                .toList();
        return new CourseResponse(entity.id, entity.name, lessons);
    }
}





