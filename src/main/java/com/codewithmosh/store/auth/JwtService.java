package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;


    public Jwt generateAccessToken(User user) {
        final long tokenExpiration = jwtConfig.getAccessTokenExpiration(); //5 min

        return generateToken(user, tokenExpiration);
    }
    public Jwt generateRefreshToken(User user) {
        final long tokenExpiration = jwtConfig.getRefreshTokenExpiration(); //7 days

        return generateToken(user, tokenExpiration);
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*tokenExpiration))
                .add("Username",user.getName()).add("Email",user.getEmail()).add("Role",user.getRole().toString())
                .build();

        return new Jwt(claims,jwtConfig.getSecret());
    }
    public Jwt parseToJwt(String token){
        try{
            var claims = getClaims(token);
            return new Jwt(claims,jwtConfig.getSecret());
        }
        catch(Exception e){
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
