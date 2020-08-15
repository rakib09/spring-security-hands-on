package com.extremecoder.springsecurityhandson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;

@SpringBootApplication
public class SpringSecurityHandsOnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityHandsOnApplication.class, args);
	}

	@Bean
	public AntPathMatcher antPathMatcher() {
		return new AntPathMatcher();
	}
}
