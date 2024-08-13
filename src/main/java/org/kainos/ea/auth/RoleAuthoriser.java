package org.kainos.ea.auth;

import io.dropwizard.auth.Authorizer;
import org.kainos.ea.models.JwtToken;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;

public class RoleAuthoriser implements Authorizer<JwtToken> {

    @Override
    public boolean authorize(JwtToken jwtToken, String role) {
        return jwtToken.getUserRole().getRoleName().equals(role);
    }

    @Override
    public boolean authorize(JwtToken principal, String role,
                             @Nullable ContainerRequestContext requestContext) {
        return principal.getUserRole().getRoleName().equals(role);
    }
}
