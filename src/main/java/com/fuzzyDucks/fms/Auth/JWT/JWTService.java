package com.fuzzyDucks.fms.Auth.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.fuzzyDucks.fms.Auth.AuthService.enums.JWTConfig;
import com.fuzzyDucks.fms.Exceptions.DecodingException;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import org.bson.Document;

public class JWTService {
    private String token = "";
    private static final ILogger logger = LoggingHandler.getInstance();
    public JWTService() {
    }

    public void signToken(Document user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTConfig.SECRET.getValue());
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
            logger.logInfo("Token signed successfully for user: " + user.getString(UserFieldName.USER_NAME.getValue()));
        } catch (Exception e) {
            logger.logWarning("Error signing token for user: " + user.getString(UserFieldName.USER_NAME.getValue()));
            System.err.println(e.getMessage());
        }
    }

    public static Object decodeObject(String token, String key) {
        try {
            Object decodedObject = JWT.decode(token).getClaim(key).as(Object.class);
            logger.logInfo("Token decoded successfully for key: " + key);
            return decodedObject;
        } catch (Exception e) {
            logger.logWarning("Error decoding token for key: " + key);
            System.err.println(e.getMessage());
        }
        throw new DecodingException("Error decoding token");
    }

    public String getToken() {
        return token;
    }

}
