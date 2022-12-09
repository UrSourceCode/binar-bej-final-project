package com.binar.flyket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class FlyketApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlyketApplication.class, args);
	}

}
