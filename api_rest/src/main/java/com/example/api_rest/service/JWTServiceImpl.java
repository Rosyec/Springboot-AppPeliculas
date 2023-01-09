package com.example.api_rest.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImpl implements JWTService{

    // Genera nuestra llave statica
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public String crearAutenticacion(Authentication authentication) throws JsonProcessingException {
       
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 * 4))
                .compact();
                System.out.println("TOKEN: " + token);
        return token;
    }

    @Override
    public Boolean validarToken(String token) {

        //String keyString = Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded());

        try {
            obtenerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    @Override
    public Claims obtenerClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(resolver(token)).getBody();
        return claims;
    }

    @Override
    public String obtenerUsername(String token) {

        return obtenerClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> obtenerRoles(String token) throws IOException{
        Object roles = obtenerClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities = Arrays
        .asList(new ObjectMapper()
            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
            .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
        
            return authorities;
    }

    @Override
    public String resolver(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        } else{
            return null;
        }
    }
    
}
