package org.kainos.ea.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Jwts;
import org.kainos.ea.models.JwtToken;
import org.kainos.ea.models.UserRole;

import java.security.Key;
import java.util.Optional;

public class JwtAuthenticator implements Authenticator<String, JwtToken> {
    Key key;

    public JwtAuthenticator(Key key) {
        this.key = key;
    }

    @Override
    public Optional<JwtToken> authenticate(String token)
            throws AuthenticationException {
        try {
            @SuppressWarnings("deprecation")
            Integer roleId = Jwts.parser().setSigningKey(key).build()
                    .parseSignedClaims(token).getPayload().get("Role",
                            Integer.class);

            JwtToken jwtToken = new JwtToken(new UserRole(roleId));

            return Optional.of(jwtToken);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
