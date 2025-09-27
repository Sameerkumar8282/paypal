package com.paypal.api_gateway.util;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    private static final String SECRET_KEY = "mySuperStrongSecretKey1234567890123456";

    private static Key getSigningKey(){
        //generate a signing key and return it
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
