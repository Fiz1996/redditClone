package com.example.reddisclone;

import com.example.reddisclone.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
public class ReddiscloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReddiscloneApplication.class, args);
	}

}
