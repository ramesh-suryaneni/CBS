package com.imagination.cbs.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

@TestConfiguration
public class TestConfig {

	@Bean
	public JavaMailSender createJavaMailSender()
	{
		return new JavaMailSenderImpl();
	}
	
	@Bean
	public UserDetailsService createUserDetailsService()
	{
		return Mockito.mock(UserDetailsService.class);
	}
}
