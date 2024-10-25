package com.aziz.lonelyapp.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.security.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

import static com.aziz.lonelyapp.security.Constants.jwtsecret;

@Component
public class JWTGenerator {
    public String generateToken(String email ){
        String user = email;
        Date currentdate = new Date();
        Date expiredate = new Date(currentdate.getTime()+ 600000000);
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
            return true;
        } catch (Exception ex){

            //return false;
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

    public static String generatateAPNToken() {

        String privateKeyPath = "KEY.p8";
        String privateKeyContent = null;
        try {
            privateKeyContent = new String(Files.readAllBytes(Paths.get(privateKeyPath)))
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Convert the string to a PrivateKey object
        byte[] pkcs8EncodedBytes = java.util.Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("EC");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        PrivateKey privateKey = null;
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        // Get the current timestamp (iat)
        long currentTimeInSeconds = System.currentTimeMillis() / 1000L;
        // Create JWT token using JJWT
        String jwtToken = Jwts.builder()
                .issuer("5B42Z2D5W6")   // iss
                .issuedAt(new Date(currentTimeInSeconds * 1000))
                .header() // iat
                .add("alg", "ES256")    // Header - alg
                .add("kid", "BH22USQ847")
                .and() // Header - kid
                .signWith(privateKey, SignatureAlgorithm.ES256) // Sign with ES256 and private key
                .compact();

    return jwtToken;
    }
}
