package controllers;

import dto.AuthRequest;
import dto.AuthResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import persistence.entities.UserEntity;
import services.AuthService;

@Path("/auth")
public class UserController {

    @EJB
    private AuthService authService;

    @POST
    @Path("/log-in")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(AuthRequest request) {
//        TODO добавить хеширование пароля
//        String password = request.getPassword();
//        password.hash();
        AuthResponse response = authService.validate(request);
        if (response.isValid()) {
            UserEntity user = new UserEntity(request.getLogin(), request.getPassword());
            response = authService.logIn(user);
            if (response.isValid()) return Response.ok(response.getMessage()).build();
            else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response.getMessage()).build();
            }
        } else return Response.status(Response.Status.BAD_REQUEST).entity(response.getMessage()).build();

    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(AuthRequest request) {
        AuthResponse response = authService.validate(request);
        if (response.isValid()) {
            UserEntity user = new UserEntity(request.getLogin(), request.getPassword());
            response = authService.register(user);
            if (response.isValid()) return Response.ok(response.getMessage()).build();
            else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response.getMessage()).build();
            }
        } else return Response.status(Response.Status.BAD_REQUEST).entity(response.getMessage()).build();
    }
}
