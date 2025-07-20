package com.skypro.StarBank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition
@EnableCaching
@SpringBootApplication
public class StarBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarBankApplication.class, args);
	}

}
