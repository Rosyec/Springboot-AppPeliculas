package com.example.api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.api_rest.models.dao.IUsuarioDao;
import com.example.api_rest.models.entity.Rol;
import com.example.api_rest.models.entity.Usuario;

@Service
public class JpaUserDetailService implements UserDetailsService{

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        Logger LOG = LoggerFactory.getLogger(JpaUserDetailService.class);

        Usuario usuario = usuarioDao.findByUsername(username);

        if (usuario == null) {
            LOG.error("ERROR", "El usuario no existe en la base de datos!");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Rol rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }

        if (authorities.isEmpty()) {
            LOG.error("ERROR", "No tienes un rol asignado");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
    
}
