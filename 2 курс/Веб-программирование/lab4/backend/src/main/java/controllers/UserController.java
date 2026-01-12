package controllers;

import dto.AuthRequest;
import dto.AuthResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import persistence.entities.UserEntity;
import services.AuthService;
import services.JwtService;

import java.util.HashMap;

@Path("/auth")
public class UserController {

    @EJB
    private AuthService authService;
    @EJB
    private JwtService jwtService;


    @POST
    @Path("/log-in")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(AuthRequest request) {
        AuthResponse response = authService.validate(request);
        if (response.isValid()) {
            UserEntity user = new UserEntity(request.getLogin(), request.getPassword());
            HashMap<String, String> loginParams = authService.logIn(user);

            return getResponse(response, loginParams);
        } else return Response.status(Response.Status.BAD_REQUEST).entity(response).build();

    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(AuthRequest request) {
        AuthResponse response = authService.validate(request);
        if (response.isValid()) {
            UserEntity user = new UserEntity(request.getLogin(), request.getPassword());
            HashMap<String, String> registerParams = authService.register(user);

            return getResponse(response, registerParams);
        } else return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }


    private Response getResponse(AuthResponse response, HashMap<String, String> loginParams) {
        if (loginParams.get("isValid").equals("true")) {
            response.setMessage(response.getMessage());
            response.setId(loginParams.get("userId"));
            NewCookie jwtCookie = jwtService.createJwtCookie(loginParams.get("jwt"));
            return Response.ok(response).cookie(jwtCookie).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
    }

}
