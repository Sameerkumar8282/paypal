package com.paypal.user_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.security.Keys;


@Component
public class JWTUtil {
    private static final String SECRET_KEY = "mySuperStrongSecretKey1234567890123456";

    private Key getSigningKey(){
        //generate a signing key and return it
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractEmail(String token){
        //extract email from token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token,String username){
        try {
            extractEmail(token); // if parsing succeeds ,token is valid
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String extractUsername(String token){
        //extract username from token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateToken(Long id, String email, String role) { // generate token,String email
        Map<String, Object> claims = Map.of("userId", id, "role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();//build
    }

    public String extractRole(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role").toString();
    }

}
