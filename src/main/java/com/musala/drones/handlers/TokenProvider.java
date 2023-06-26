package com.musala.drones.handlers;


import com.musala.drones.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import static com.musala.drones.util.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.musala.drones.util.Constants.AUTHORITIES_KEY;
import static com.musala.drones.util.Constants.SIGNING_KEY;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class TokenProvider implements Serializable {

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserEntity userEntity) {
        final String authorities = userEntity.getRole();
        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact();
    }

}
