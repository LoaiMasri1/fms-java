package com.fuzzyDucks.fms.Auth.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.fuzzyDucks.fms.User.enums.UserFieldName;
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
                    .withClaim(UserFieldName.USER_NAME.getValue(), user.getString(UserFieldName.USER_NAME.getValue()))
                    .withClaim(UserFieldName.EMAIL.getValue(), user.getString(UserFieldName.EMAIL.getValue()))
                    .withClaim(UserFieldName.ROLE.getValue(), user.getInteger(UserFieldName.ROLE.getValue()))
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

    public static String decodeObject(String token, String key) {
        try {
            return JWT.decode(token).getClaim(key).asString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String getToken() {
        return token;
    }

}
