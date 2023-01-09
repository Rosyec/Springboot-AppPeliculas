package com.example.api_rest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api_rest.models.dao.IPeliculaDao;
import com.example.api_rest.models.entity.Pelicula;

@Service
public class PeliculaServiceImpl implements IPeliculaService{

    @Autowired
    private IPeliculaDao peliculaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> buscarTodos() {
        // TODO Auto-generated method stub
        return (List<Pelicula>) this.peliculaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Pelicula buscarPorId(Pelicula pelicula) {
        // TODO Auto-generated method stub
        return this.peliculaDao.findById(pelicula.getId()).orElse(null);
    }

    @Override
    @Transactional
    public Pelicula guardarPelicula(Pelicula pelicula) {
        // TODO Auto-generated method stub
        return this.peliculaDao.save(pelicula);
    }

    @Override
    @Transactional
    public void eliminarPorId(Pelicula pelicula) {
        // TODO Auto-generated method stub
        this.peliculaDao.delete(pelicula);
    }

    @Override
    @Transactional
    public void actualizarPelicula(Pelicula pelicula) {
        // TODO Auto-generated method stub
        Pelicula pelicula2 = this.buscarPorId(pelicula);
        if (pelicula2 == null) {
            this.peliculaDao.save(pelicula);
        }else{
            pelicula2.setNombre(pelicula.getNombre());
            pelicula2.setGenero(pelicula.getGenero());
            this.peliculaDao.save(pelicula2);
        }
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> buscarPorNombre(String term) {
        return this.peliculaDao.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> buscarPorGenero(String term) {
        return this.peliculaDao.findByGeneroLikeIgnoreCase("%" + term + "%");
    }
    
}
