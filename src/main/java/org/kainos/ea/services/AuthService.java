package org.kainos.ea.services;


import org.kainos.ea.daos.AuthDao;
import org.kainos.ea.exceptions.Entity;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.models.LoginRequest;
import org.kainos.ea.models.User;

import java.security.Key;
import java.sql.SQLException;
import java.util.Date;

import io.jsonwebtoken.Jwts;


public class AuthService {
    final private AuthDao authDao;
    final private Key key;

    public AuthService(AuthDao authDao, Key key) {
        this.authDao = authDao;
        this.key = key;
    }

    public String login(LoginRequest loginRequest)
            throws SQLException, InvalidException {
        User user = authDao.getUser(loginRequest);

        if (user == null) {
            throw new InvalidException(Entity.USER, "Invalid credentials");
        }

        return generateJwtToken(user);
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 28800000))
                .claim("Role", user.getRoleId())
                .subject(user.getUsername())
                .issuer("DropwizardDemo")
                .signWith(key)
                .compact();
    }
}
