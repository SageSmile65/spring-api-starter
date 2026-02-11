package com.codewithmosh.store.services;

import com.codewithmosh.store.config.JwtConfig;
import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;


    public String generateAccessToken(User user) {
        final long tokenExpiration = jwtConfig.getAccessTokenExpiration(); //5 min

        return generateToken(user, tokenExpiration);
    }
    public String generateRefreshToken(User user) {
        final long tokenExpiration = jwtConfig.getRefreshTokenExpiration(); //7 days

        return generateToken(user, tokenExpiration);
    }

    private String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .claims().add("Username", user.getName()).add("Email", user.getEmail()).add("Role",user.getRole().toString())
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
                .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String getUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }
    public String getUserRoleFromToken(String token){
        return getClaims(token).get("Role").toString();
    }
}
