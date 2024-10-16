package com.spring.batch.trial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrialApplication {


	public static void main(String[] args) {
		SpringApplication.run(TrialApplication.class, args);
		System.out.println("this is our spring batch trial");
	}

}
