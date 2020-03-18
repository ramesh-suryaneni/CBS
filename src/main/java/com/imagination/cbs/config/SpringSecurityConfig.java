/*
package com.imagination.cbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.imagination.cbs.security.GoogleIDTokenValidationFilter;



@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GoogleIDTokenValidationFilter googleIDTokenValidationFilter;
	
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
	        http.cors().and().csrf().disable().antMatcher("/**").
	                authorizeRequests().antMatchers("/", "/index.html").permitAll()
	                
	                //** BOTH ADMIN/USER CAN ACCESS **//*
	                //** ONLY ADMIN CAN ACCESS **//*
	                .anyRequest().authenticated().and().addFilterBefore(googleIDTokenValidationFilter,UsernamePasswordAuthenticationFilter.class);
	                
	    }
	    
	  

}*/