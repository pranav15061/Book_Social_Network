package com.pranav.book_network.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtService {


    private String secretKey="b2798d89048d85246257b3854c55d4c9475fffd7d4edc3b8a2c2124d0b7e8660b4da995c7459d728979fe60e3984e38641cb84e4a888f636704b9e74d00513f3";
    private long jwtExpiration=172800000;


    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey()) // Set the key used for signing the token
                .build()
                .parseSignedClaims(token) // Parses and verifies the token
                .getPayload()
                .getSubject(); // Extracts the "sub" (subject) field
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        var authorities = userDetails.getAuthorities()
                .stream().
                map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
//                .setClaims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
         return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey()) // Set the key used for signing the token
                .build()
                .parseSignedClaims(token) // Parses and verifies the token
                .getPayload()
                .getExpiration();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
