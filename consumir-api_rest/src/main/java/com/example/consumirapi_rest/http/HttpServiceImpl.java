package com.example.consumirapi_rest.http;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.consumirapi_rest.models.Pelicula;

@Service
public class HttpServiceImpl implements IHttpService{

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SolicitarToken solicitarToken;

    private static final String URL_LISTAR = "http://localhost:8080/api/listar";
    private static final String URL_LISTAR_GENERO = "http://localhost:8080/api/listar-genero";
    private static final String URL_API = "http://localhost:8080/api/pelicula";

    @Override
    //@SuppressWarnings("unchecked")
    public Map<String, Object> buscarTodos() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<String> entity = new HttpEntity<String>("body", headers);
        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(URL_LISTAR, HttpMethod.GET, entity, typeRef);

        Map<String, Object> response = result.getBody();

        return response;
    }

    @Override
    public Map<String, Object> buscarPorNombre(String term) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<String> entity = new HttpEntity<String>("body", headers);
        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(URL_LISTAR.concat("/" + term), HttpMethod.GET, entity, typeRef);

        Map<String, Object> response = result.getBody();

        return response;
    }

    @Override
    public Map<String, Object> buscarPorGenero(String term) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<String> entity = new HttpEntity<String>("body", headers);
        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(URL_LISTAR_GENERO.concat("/" + term), HttpMethod.GET, entity, typeRef);

        Map<String, Object> response = result.getBody();

        return response;
    }

    @Override
    public Pelicula buscarPorId(Pelicula p) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<String> entity = new HttpEntity<String>("body", headers);
        ResponseEntity<Pelicula> result = restTemplate.exchange(URL_API.concat("/" + p.getId()), HttpMethod.GET, entity, Pelicula.class);

        Pelicula pelicula = result.getBody();

        return pelicula;
    }

    @Override
    public Pelicula guardar(Pelicula p) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<Pelicula> entity = new HttpEntity<Pelicula>(p, headers);
        ResponseEntity<Pelicula> result = restTemplate.exchange(URL_API, HttpMethod.POST, entity, Pelicula.class);

        Pelicula pelicula = result.getBody();

        return pelicula;
    }

    @Override
    public String actualizar(Pelicula p) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<Pelicula> entity = new HttpEntity<Pelicula>(p, headers);
        ResponseEntity<String> result = restTemplate.exchange(URL_API.concat("/" + p.getId()), HttpMethod.PUT, entity, String.class);

        return result.getStatusCode().toString();
    }

    @Override
    public String eliminar(Pelicula p) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + solicitarToken.accesToken().getToken());

        HttpEntity<String> entity = new HttpEntity<String>("body", headers);
        ResponseEntity<String> result = restTemplate.exchange(URL_API.concat("/" + p.getId()), HttpMethod.DELETE, entity, String.class);

        return result.getStatusCode().toString();
    }
    
}
