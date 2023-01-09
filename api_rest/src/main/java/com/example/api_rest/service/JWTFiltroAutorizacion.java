package com.example.api_rest.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTFiltroAutorizacion extends BasicAuthenticationFilter{

    private JWTService jwtService;

    private static final String AUTHORIZATION_NAME = "Authorization";

    public JWTFiltroAutorizacion(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        String header = request.getHeader(AUTHORIZATION_NAME);
        if (!requiereAutenticacion(header)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = null;

        if (jwtService.validarToken(header)) {
            authenticationToken = new UsernamePasswordAuthenticationToken(jwtService.obtenerUsername(header), null, jwtService.obtenerRoles(header));
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    protected Boolean requiereAutenticacion(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        } else {
            return true;
        }
    }
    
}
