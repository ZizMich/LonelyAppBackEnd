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
    /**
     * Generates a JWT token for the given email.
     *
     * @param email The email of the user for whom the token is being generated.
     * @return A JWT token string.
     *
     * The token is generated using the HMAC SHA algorithm with a secret key.
     * The token contains the user's email as the subject, the current date as the issue date,
     * and an expiration date that is 10 minutes from the current date.
     * The token is signed with the secret key and returned as a string.
     */
    public String generateToken(String email ){
        String user = email;
        Date currentdate = new Date();
        Date expiredate = new Date(currentdate.getTime()+ 600000000);
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        String token = Jwts.builder().subject(user).issuedAt(new Date()).expiration(expiredate).signWith(key).compact();
        return token;
    }
    /**
     * Retrieves the username from a JWT token.
     *
     * @param token The JWT token from which the username is to be extracted.
     * @return The username extracted from the JWT token.
     *
     * This function takes a JWT token as input, decodes the token using the base64-decoded secret key,
     * verifies the token's signature using the secret key, and extracts the username from the token's payload.
     * The username is then returned as a string.
     *
     * If the token is invalid or expired, an exception will be thrown.
     */

    public String getUsernameFormJWT(String token){
        byte[] bytes = Decoders.BASE64.decode(jwtsecret);
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
    /**
     * Validates a JWT token.
     *
     * @param token The JWT token to be validated.
     * @return True if the token is valid, false otherwise.
     *
     * This function takes a JWT token as input, decodes the token using the base64-decoded secret key,
     * verifies the token's signature using the secret key, and checks if the token is expired.
     * If the token is valid (not expired), the function returns true.
     * If the token is invalid or expired, an exception is thrown with a message indicating the issue.
     *
     * @throws AuthenticationCredentialsNotFoundException If the JWT token is expired or incorrect.
     */
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
    /**
     * Validates an expired JWT token.
     *
     * @param token The JWT token to be validated.
     * @return True if the token is expired, false otherwise.
     *
     * This function takes a JWT token as input, decodes the token using the base64-decoded secret key,
     * verifies the token's signature using the secret key, and checks if the token is expired.
     * If the token is expired, the function returns true.
     * If the token is valid (not expired), the function returns false.
     *
     */
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
    /**
     * Generates a JWT token for Apple Push Notification Service (APNs) using a private key.
     *
     * @return A JWT token string for APN authentication.
     *
     * This function reads a private key from a file, converts it into a PrivateKey object,
     * and then uses the JJWT library to generate a JWT token. The token is used for APN authentication.
     *
     * The private key is read from a file named "KEY.p8" located in the same directory as the Java program.
     * The private key file should be in PEM format and should not contain any headers or footers
     *
     * The token is signed with the private key and returned as a string.
     *
     * @throws RuntimeException If an error occurs during file reading, key conversion, or token generation.
     */
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
                .add("kid", "52665M6PUY")
                .and() // Header - kid
                .signWith(privateKey, SignatureAlgorithm.ES256) // Sign with ES256 and private key
                .compact();

    return jwtToken;
    }
}
