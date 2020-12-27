package com.ncedu.cheetahtest.config.security.jwt;

import com.ncedu.cheetahtest.entity.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProviderImpl implements JwtTokenProvider{

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.exp}")
    private long expirationTime;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Override
    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole());
        claims.put("name", user.getName());
        claims.put("id", user.getId());

        Date now = new Date();
        Date expiredMs = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredMs)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public String getEmail(String token) {
        return  Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            return false;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
    }
}
