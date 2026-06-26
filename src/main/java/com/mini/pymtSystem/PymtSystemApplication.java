package com.mini.pymtSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PymtSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PymtSystemApplication.class, args);
	}

}
