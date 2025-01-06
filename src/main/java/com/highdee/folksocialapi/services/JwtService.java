package com.highdee.folksocialapi.services;

public interface JwtService {

    String generateToken(String email);

    String extractEmail(String token);

    boolean validateToken(String token, String email);

    boolean isTokenExpired(String token);
}
