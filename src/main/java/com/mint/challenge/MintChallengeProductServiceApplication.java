package com.mint.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MintChallengeProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MintChallengeProductServiceApplication.class, args);
	}

}
