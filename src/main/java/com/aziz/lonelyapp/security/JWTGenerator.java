package com.aziz.lonelyapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.aziz.lonelyapp.security.Constants.exp;
import static com.aziz.lonelyapp.security.Constants.jwtsecret;
import static org.springframework.security.config.Elements.JWT;

@Component
public class JWTGenerator {
    /**
     * Generates a JSON Web Token (JWT) for the given authentication.
     *
     * @param authentication the authentication object containing the user name
     * @return the generated JWT as a string
     */
    public String generateToken(Authentication authentication) {
        String user = authentication.getName();
        Date currentdate = new Date();
        Date expiredate = new Date(currentdate.getTime() + 70000000);
        System.out.println(expiredate.getTime());
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        String token = Jwts.builder().subject(user).issuedAt(new Date()).expiration(expiredate).signWith(key).compact();
        return token;
    }

    /**
     * Retrieves the username from a JSON Web Token (JWT).
     *
     * @param token the JWT from which to extract the username
     * @return the username extracted from the JWT
     */
    public String getUsernameFormJWT(String token) {
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    /**
     * Validates a JSON Web Token (JWT) and returns true if it is valid.
     *
     * @param token the JWT to be validated
     * @return true if the JWT is valid, false otherwise
     * @throws AuthenticationCredentialsNotFoundException if the JWT is expired or
     *                                                    incorrect
     **/
    public boolean validateToken(String token) {
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT is expired or incorrect");
        }

    }

}
