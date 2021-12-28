package com.example.reddisclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReddiscloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReddiscloneApplication.class, args);
	}

}
