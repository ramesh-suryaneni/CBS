
package com.imagination.cbs.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.imagination.cbs.security.GoogleAccessTokenValidationFilter;
import com.imagination.cbs.security.GoogleAuthenticationProvider;



@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GoogleAuthenticationProvider googleAuthenticationProvider;
	
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	      web
	        .ignoring()
	        .antMatchers("/token/*", 
            		"/v2/**",
                    "/configuration/**",
                    "/migration/**",
                    "/swagger-resources/**",
                    "/swagger-ui.html",
                    "/webjars/**");
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable().
	                authorizeRequests().antMatchers("/", "/index.html").permitAll()
	                
	                //** BOTH ADMIN/USER CAN ACCESS **//*
	                //** ONLY ADMIN CAN ACCESS **//*
	                .anyRequest().authenticated().and().addFilter(getGoogleAccessTokenValidationFilter());
	                
	    }
	    
	    private BasicAuthenticationFilter getGoogleAccessTokenValidationFilter(){
			
			List<AuthenticationProvider> listOfAuthenticationProvider=new ArrayList<>();
			listOfAuthenticationProvider.add(googleAuthenticationProvider);
			
			AuthenticationManager manager=new ProviderManager(listOfAuthenticationProvider);
			
			return new GoogleAccessTokenValidationFilter(manager);
		}

}