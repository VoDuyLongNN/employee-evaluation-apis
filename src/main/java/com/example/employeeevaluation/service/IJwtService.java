package com.example.employeeevaluation.service;

import com.example.employeeevaluation.entity.User;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(User user);

    String generateToken(Map<String, Object> claims, User user);

    public String generateRefreshToken(User user);

    Claims extractAllClaims(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Integer getUserId(String token);

    Date getExpirationTimeFromToken(String token, String secretKey);

    String extractUsernameFromToken(String token);

    Date extractExpirationFromToken(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
