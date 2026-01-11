package filters;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

import persistence.dao.UserDAO;
import services.JwtService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {
    @EJB
    JwtService jwtService;
    @Inject
    UserDAO db;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtService.extractSubject(token);
            Cookie jwtCookie = requestContext.getCookies().get("JWT");
        }

    }
}
