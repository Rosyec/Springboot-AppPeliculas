package com.example.api_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.api_rest.models.entity.Pelicula;
import com.example.api_rest.models.service.IPeliculaService;

@Controller
@RequestMapping("/app")
public class IndexController {

    @Autowired
    private IPeliculaService peliculaService;

    @GetMapping("/index")
    public String crear(){

        Pelicula pelicula = new Pelicula();
        pelicula.setNombre("Spider-Man");
        pelicula.setGenero("Accion");
        peliculaService.guardarPelicula(pelicula);
        
        return "index";
    }

}
