package filters;

import dto.AuthResponse;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

import services.JwtService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {
    @EJB
    JwtService jwtService;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("/auth")) return;

        Cookie jwtCookie = requestContext.getCookies().get("JWT");
        String clientToken = (jwtCookie != null) ? jwtCookie.getValue() : null;

        if (clientToken == null || clientToken.isEmpty() || !jwtService.tokenIsValid(clientToken)) {
            AuthResponse response = new AuthResponse(false, "Отклонено в доступе!");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(response).build());
        }
    }
}

