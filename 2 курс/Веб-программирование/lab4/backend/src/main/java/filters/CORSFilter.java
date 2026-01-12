package filters;

import jakarta.ws.rs.container.*;
        import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if (isPreflightRequest(request)) {
            request.abortWith(Response.ok().build());
        }
    }

    private boolean isPreflightRequest(ContainerRequestContext request) {
        return request.getRequest().getMethod().equalsIgnoreCase("OPTIONS");
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        response.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}