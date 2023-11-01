package dev.devesh.reviewmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ReviewManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewManagementApplication.class, args);
	}

}
