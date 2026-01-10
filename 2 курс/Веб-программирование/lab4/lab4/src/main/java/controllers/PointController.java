package controllers;

import dto.PointRequest;
import dto.PointResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import persistence.entities.PointEntity;
import services.PointService;
import utility.exceptions.UserNotFoundException;

import java.math.BigDecimal;


@Path("/points/{userId}")
public class PointController {

    @Inject
    private PointService pointService;

    @POST
    @Path("/add-point")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPoint(PointRequest request, @PathParam("userId") Long userId) {
        try {
            long startTime = System.nanoTime();

            BigDecimal x = new BigDecimal(request.getX());
            BigDecimal y = new BigDecimal(request.getY());
            BigDecimal r = new BigDecimal(request.getR());
            PointResponse response = pointService.validate(request);
            if (response.isValid()) {
                PointEntity point = new PointEntity(x, y, r);
//                point.setUserId(userId.toString()); // TODO
                response = pointService.add(point, startTime, userId);
                return Response.ok().entity(response).build();
            } else return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/get-points")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints(@PathParam("userId") Long userId) {
        PointResponse response = pointService.getPointsById(userId);
        if (response.isValid()) {
            return Response.ok().entity(response).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }
}
