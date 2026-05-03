package com.microservices.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DepartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentApplication.class, args);
	}

}
