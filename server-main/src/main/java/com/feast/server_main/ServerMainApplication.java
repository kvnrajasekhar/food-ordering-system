package com.feast.server_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude=SecurityAutoConfiguration.class)
public class ServerMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerMainApplication.class, args);
	}

}
