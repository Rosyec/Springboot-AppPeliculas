package com.example.consumirapi_rest;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.consumirapi_rest.service.IUploadService;

@SpringBootApplication
public class ConsumirApiRestApplication {

	@Autowired
	private IUploadService uploadService;	

	public static void main(String[] args) {
		SpringApplication.run(ConsumirApiRestApplication.class, args);
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler((ResponseErrorHandler) new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        //do nothing
                    }
                })
                .build();
    }

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {
			//uploadService.borrarDirectorio();
			//uploadService.crearDirectorio();
		};

	}

}
