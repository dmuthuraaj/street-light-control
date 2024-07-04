package com.opzero.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.opzero.*")
@SpringBootApplication(scanBasePackages = {"com.opzero.*"})
public class IotProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotProjectApplication.class, args);
	}

}
