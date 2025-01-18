package com.app.treen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.app.treen.jpa.repository")
@EnableMongoRepositories(basePackages = "com.app.treen.mongo")
@SpringBootApplication
public class TreenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreenApplication.class, args);
	}

}
