package com.fuzzyDucks.fms.Auth.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import org.bson.Document;

public class JWTService {
    private Algorithm algorithm;
    private String token = "";

    public JWTService() {
    }

    public void signToken(Document user) {
        try {
            algorithm = Algorithm.HMAC256(JWTConfig.SECRET.getValue());
            token = JWT.create().withIssuer(JWTConfig.ISSUER.getValue())
                    .withClaim("username", user.getString("username"))
                    .withClaim("email", user.getString("email"))
                    .withClaim("role", user.getInteger("role"))
                    .withClaim("sub", JWTConfig.SUBJECT.getValue())
                    .withExpiresAt(
                            new Date(System.currentTimeMillis() + Long.parseLong(JWTConfig.EXPIRATION_TIME.getValue())))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getToken() {
        return token;
    }

}
