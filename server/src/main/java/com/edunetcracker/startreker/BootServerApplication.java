package com.edunetcracker.startreker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class BootServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootServerApplication.class, args);
	}
}
