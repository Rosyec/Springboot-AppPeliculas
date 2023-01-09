package com.example.api_rest.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.api_rest.models.entity.Pelicula;

public interface IPeliculaDao extends CrudRepository<Pelicula, Long>{
    public List<Pelicula> findByNombreLikeIgnoreCase(String nombre);
    public List<Pelicula> findByGeneroLikeIgnoreCase(String genero);
}
