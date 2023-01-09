package com.example.api_rest.models.service;

import java.util.List;

import com.example.api_rest.models.entity.Pelicula;

public interface IPeliculaService {
    public List<Pelicula> buscarTodos();
    public List<Pelicula> buscarPorNombre(String term);
    public List<Pelicula> buscarPorGenero(String term);
    public Pelicula buscarPorId(Pelicula usuario);
    public Pelicula guardarPelicula(Pelicula usuario);
    public void actualizarPelicula(Pelicula usuario);
    public void eliminarPorId(Pelicula usuario);
}
