package com.diziperest.web;

import com.diziperest.web.services.concretes.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Autowired
	private StorageService storageService;

	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
			storageService.init();
		};
	}
}
