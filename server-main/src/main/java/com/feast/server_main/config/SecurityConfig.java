package com.feast.server_main.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class SecurityConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") 
				.allowedOrigins("http://127.0.0.1:5500") 
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
				.allowedHeaders("*")
				.allowCredentials(true) 
				.maxAge(3600);
	}
}
