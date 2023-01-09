package com.example.api_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.api_rest.service.JWTFiltroAutenticacion;
import com.example.api_rest.service.JWTFiltroAutorizacion;
import com.example.api_rest.service.JWTService;
import com.example.api_rest.service.JpaUserDetailService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JpaUserDetailService userDetailService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JWTService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
        .authorizeRequests()
        .antMatchers("/", "/app/index", "/app/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTFiltroAutenticacion(authenticationConfiguration.getAuthenticationManager(), jwtService))
        .addFilter(new JWTFiltroAutorizacion(authenticationConfiguration.getAuthenticationManager(), jwtService))
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return security.build();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception{
        PasswordEncoder passwordEncoder = this.bCryptPasswordEncoder;
        managerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

}
