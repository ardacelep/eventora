package com.ardacelep.eventora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.ardacelep.eventora")
public class EventoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoraApplication.class, args);
	}

}
