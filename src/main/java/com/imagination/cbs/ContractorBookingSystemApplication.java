package com.imagination.cbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.imagination.cbs.security.GoogleIDTokenValidationFilter;

@SpringBootApplication
@EnableCaching
public class ContractorBookingSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(ContractorBookingSystemApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	// this removes filter in case of local profile
	@Bean
	@Profile("local")
	public FilterRegistrationBean<GoogleIDTokenValidationFilter> registration(GoogleIDTokenValidationFilter filter) {
	    
		FilterRegistrationBean<GoogleIDTokenValidationFilter> registration = new FilterRegistrationBean<GoogleIDTokenValidationFilter>(filter);
	    registration.setEnabled(false);
	   
	    
	    return registration;
	}
}
