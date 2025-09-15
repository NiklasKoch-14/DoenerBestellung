package com.example.doenerbestellung.service;

import com.example.doenerbestellung.entity.Role;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secret;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private SecretKey secretKey;

    private JwtParser jwtParser;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        // Encode and build a proper HMAC SecretKey
        byte[] keyBytes = Base64.getEncoder().encode(secret.getBytes());
        secretKey = Keys.hmacShaKeyFor(keyBytes);

        // Create a thread-safe JwtParser that uses verifyWith(...)
        jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public String createToken(String username, Set<Role> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        return "Bearer " + Jwts.builder()
                .claim("roles", roles)
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token).getPayload().getSubject();

    }

    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        return (auth != null && auth.startsWith("Bearer ")) ? auth.substring(7) : null;
    }

    public boolean validateToken(String token) {
        try {

            Jws<Claims> jwts = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token", e);
        }
    }
}
