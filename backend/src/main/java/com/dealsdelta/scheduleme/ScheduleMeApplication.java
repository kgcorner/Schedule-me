package com.dealsdelta.scheduleme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduleMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleMeApplication.class, args);
	}

}
