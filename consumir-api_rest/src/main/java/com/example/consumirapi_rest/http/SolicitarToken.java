package com.example.consumirapi_rest.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.consumirapi_rest.models.Response;

@Component
public class SolicitarToken {

    @Autowired
    private RestTemplate restTemplate;

    Logger LOG = LoggerFactory.getLogger(getClass());
    
    private static final String URL_ACCESS = "http://localhost:8080/access/login";
    private static final String USERNAME = "ceysor";
    private static final String PASSWORD = "12345";
    
    public Response accesToken() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_ACCESS);
        builder.queryParam("username", USERNAME);
        builder.queryParam("password", PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<String> requestEntity = new HttpEntity<String>("body", headers);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, Response.class);

        //LOG.info("TOKEN: " + responseEntity.getBody().getToken());
        
        return responseEntity.getBody();
    }
}
