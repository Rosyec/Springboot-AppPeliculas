package com.example.api_rest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_rest.models.entity.Pelicula;
import com.example.api_rest.models.service.IPeliculaService;

@CrossOrigin(origins = {"http://localhost:8090", "*"})
@RestController()
@RequestMapping("/api")
@Secured("ROLE_ADMIN")
public class RestFullController {

    @Autowired
    private IPeliculaService iClienteService;

    Logger LOG = LoggerFactory.getLogger(RestFullController.class);
    
    @GetMapping("/listar")
    public ResponseEntity<?> todos(){

        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculas = null;
        peliculas = (List<Pelicula>) iClienteService.buscarTodos();

        if (peliculas.isEmpty()) {
            response.put("message", "No hay peliculas en la base de datos");
            response.put("error", true);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("message", "Lista de peliculas actualizada!");
        response.put("error", false);
        response.put("peliculas", peliculas);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        
    }

    @GetMapping("listar/{term}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String term){
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculas = null;
        peliculas = this.iClienteService.buscarPorNombre(term);
        if (peliculas.isEmpty()) {
            response.put("message", "No se encontraron películas en la base de datos con el termino **" + term.toUpperCase() +"**." );
            response.put("error", true);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Se encontraron las siguientes peliculas!");
        response.put("peliculas", peliculas);
        response.put("error", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("listar-genero/{term}")
    public ResponseEntity<?> buscarPorGenero(@PathVariable String term){
        Map<String, Object> response = new HashMap<>();
        List<Pelicula> peliculas = null;
        peliculas = this.iClienteService.buscarPorGenero(term);
        if (peliculas.isEmpty()) {
            response.put("message", "No se encontrarón películas en la categoria ".concat(term.toUpperCase()));
            response.put("error", true);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Se encontraron las siguientes peliculas!");
        response.put("peliculas", peliculas);
        response.put("error", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/pelicula/{id}")
    public Pelicula buscar(@PathVariable Long id){
        LOG.info("GET - BUSCAR: " + id);
        return iClienteService.buscarPorId(new Pelicula(id));
    }

    @PostMapping("/pelicula")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pelicula guardar(@RequestBody Pelicula pelicula){
        return iClienteService.guardarPelicula(pelicula);
    }

    @PutMapping("/pelicula/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void actualizar(@RequestBody Pelicula pelicula, @PathVariable Long id){
        Pelicula p = iClienteService.buscarPorId(new Pelicula(id));
        p.setNombre(pelicula.getNombre());
        p.setGenero(pelicula.getGenero());
        iClienteService.actualizarPelicula(p);
    }

    @DeleteMapping("/pelicula/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        iClienteService.eliminarPorId(new Pelicula(id));
    }
    
}
