package org.example.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.BotUser;
import org.example.entities.Volunteer;
import org.example.exceptions.VolunteerNotFoundException;
import org.example.repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements org.example.services.JwtService {
    private final VolunteerRepository volunteerRepository;
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public JwtServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }


    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    @Override
    public String generateToken(UserDetails userDetails) {
        Volunteer volunteer = volunteerRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new VolunteerNotFoundException(userDetails.getUsername()));
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof BotUser customUserDetails) {
            claims.put("id", volunteer.getId());
            claims.put("email", customUserDetails.getEmail());
        }
        return generateToken(claims, userDetails);
    }


    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }


    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}