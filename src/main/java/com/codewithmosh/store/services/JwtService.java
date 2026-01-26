package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.JwtResponse;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {


    @Value("${spring.jwt.secret}")
    private String secret;


    public String generateAccessToken(User user) {
        final long tokenExpiration = 300; //5 min

        return generateToken(user, tokenExpiration);
    }
    public String generateRefreshToken(User user) {
        final long tokenExpiration = 604800; //7 days

        return generateToken(user, tokenExpiration);
    }

    private String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .claims().add("Username", user.getName()).add("Email", user.getEmail())
                .and().compact();
    }

    public boolean validateToken(String token) throws JwtException {
        try{
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());

        }
        catch (JwtException e){
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String getUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }
}
