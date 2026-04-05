package com.courses.resource;

import com.courses.dto.LessonCreateRequest;
import com.courses.dto.LessonResponse;
import com.courses.service.LessonService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


import java.net.URI;
import java.util.List;

@Path("/courses/{courseId}/lessons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LessonResource {

    @Inject
    LessonService lessonService;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@PathParam("courseId") Long courseId,
                           @Valid LessonCreateRequest request,
                           @Context UriInfo uriInfo) {

        LessonResponse response = lessonService.create(courseId, request);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(response.id()))
                .build();

        return Response.created(location)
                .entity(response)
                .build();
    }
    @GET
    @PermitAll
    public Response list(@PathParam("courseId") Long courseId) {
        List<LessonResponse> response = lessonService.listByCourse(courseId);


        return Response.ok(response)
                .build();


    }


}




