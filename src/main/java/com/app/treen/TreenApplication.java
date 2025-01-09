package com.app.treen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class TreenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreenApplication.class, args);
	}

}
