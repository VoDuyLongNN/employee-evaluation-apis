package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.entity.User;
import com.example.employeeevaluation.util.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Data
@SuppressWarnings({"deprecation", "unused"})
public class JwtServiceImpl {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Integer jwtExpirationInMs;

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> claims, User user) {
        claims.put("UserId", user.getUserId());
        ZonedDateTime expirationTime = ZonedDateTime.now().plusHours(jwtExpirationInMs);
        Date expire = Date.from(expirationTime.toInstant());

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expire).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("UserId", user.getUserId());
        ZonedDateTime expirationTime = ZonedDateTime.now().plusDays(7);
        Date expire = Date.from(expirationTime.toInstant());

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expire).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Integer getUserId(String token) {
        return this.extractClaim(token, claims -> claims.get("UserId", Integer.class));
    }

    public Date getExpirationTimeFromToken(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsernameFromToken(token);

        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
