package com.example.HenriPank.config;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET; //Hidden in real world, bank secret key used for signing token

    private Key key;

    //When spring has gotten its Secret, it will automatically call init to get the KEY
    @PostConstruct
    private void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
    /**
     * Generates a secure JWT for a specific user.
     * The token includes the user's email as the subject, issuance time, and an expiration date.
     *
     * @param email The user's unique email address.
     * @return A signed JWT string valid for 24 hours.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //Expires after 24h
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Decodes a JWT and retrieves the user email stored within it.
     *
     * @param token The signed JWT string to be parsed.
     * @return The email address associated with the token.
     */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
