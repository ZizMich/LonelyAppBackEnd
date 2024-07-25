package com.aziz.lonelyapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
    public String generateToken(String email ){
        String user = email;
        Date currentdate = new Date();
        Date expiredate = new Date(currentdate.getTime()+ 700000);
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        String token = Jwts.builder().subject(user).issuedAt(new Date()).expiration(expiredate).signWith(key).compact();
        return token;
    }

    public String getUsernameFormJWT(String token){
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        try{
            System.out.println(Jwts.parser().verifyWith(key).build().parseSignedClaims(token));
            return true;
        } catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT is expired or incorrect");
        }

    }
    public boolean validateExpiredToken(String token){
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        try{
            System.out.println(Jwts.parser().verifyWith(key).build().parseSignedClaims(token));
            return true;
        } catch (Exception ex){
            if (ex instanceof ExpiredJwtException){
                return true;
            }
            return false;
        }

    }

}
