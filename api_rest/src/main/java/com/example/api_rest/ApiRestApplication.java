package com.example.api_rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class ApiRestApplication implements CommandLineRunner{

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = "12345";
		for(int i = 0; i<2; i++){
			String bCryptPassword = this.passwordEncoder.encode(password);
			System.out.println(bCryptPassword);
		}

	}

}
