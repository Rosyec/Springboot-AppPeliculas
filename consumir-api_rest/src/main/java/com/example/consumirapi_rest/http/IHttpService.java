package com.example.consumirapi_rest.http;

import java.util.Map;

import com.example.consumirapi_rest.models.Pelicula;

public interface IHttpService {
    public Map<String, Object> buscarTodos();
    public Map<String, Object> buscarPorNombre(String term);
    public Map<String, Object> buscarPorGenero(String term);
    public Pelicula buscarPorId(Pelicula pelicula);
    public Pelicula guardar(Pelicula pelicula);
    public String actualizar(Pelicula pelicula);
    public String eliminar(Pelicula pelicula);
}
