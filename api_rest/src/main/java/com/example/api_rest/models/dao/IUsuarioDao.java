package com.example.api_rest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.api_rest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
    
    public Usuario findByUsername(String username);
}
