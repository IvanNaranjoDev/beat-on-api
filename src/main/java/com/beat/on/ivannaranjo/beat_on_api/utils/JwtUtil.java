package com.beat.on.ivannaranjo.beat_on_api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Autowired
    private KeyPair jwtKeyPair;

    private static final long JWT_EXPIRATION = 3600000;

    @Value("${jwt.secret}")
    private String secretKeyFromProperties;

    private SecretKey getSingingKey(){
        return Keys.hmacShaKeyFor(secretKeyFromProperties.getBytes());
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(jwtKeyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(String username, List<String> roles){
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(jwtKeyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();
    }

    public boolean validateToken(String token, String username){
        Claims claims = Jwts.parser()
                .verifyWith(jwtKeyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return username.equals(claims.getSubject()) && !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
