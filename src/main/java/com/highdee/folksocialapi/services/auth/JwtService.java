package com.highdee.folksocialapi.services.auth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${folk.social.api.secretkey}")
    private String secretkey;

    private Key getKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        Key key = this.getKey();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        Key key = this.getKey();
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Key key = this.getKey();
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
