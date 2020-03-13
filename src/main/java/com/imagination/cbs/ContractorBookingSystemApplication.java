package com.imagination.cbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.imagination.cbs.service.impl.AdobeOAuthTokensServiceImpl;

@SpringBootApplication
public class ContractorBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractorBookingSystemApplication.class, args);
	}

	@Autowired
	AdobeOAuthTokensServiceImpl adobe;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
