package com.example.api_rest.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;

public interface JWTService {
    public String crearAutenticacion(Authentication authentication)throws JsonProcessingException;
    public Boolean validarToken(String token);
    public Claims obtenerClaims(String token);
    public String obtenerUsername(String token);
    public Collection<? extends GrantedAuthority> obtenerRoles(String token)throws IOException;
    public String resolver(String token);
}
