package com.pet_project.pastebin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(
//		exclude = {MongoAutoConfiguration.class}
)
@EnableMongoAuditing
public class PastebinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastebinApplication.class, args);
	}
}
