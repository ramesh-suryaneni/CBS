
package com.imagination.cbs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.imagination.cbs.security.GoogleIDTokenValidationFilter;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger=LoggerFactory.getLogger(SpringSecurityConfig.class);
	
	@Value("${cbssecurity.enabled:true}")
	private boolean securityEnabled;
	
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
	    	
	    	logger.info("SecurityEnabled" + securityEnabled);
	    	
	    	if(securityEnabled){
	    		logger.info("Security enabled");
	    		http.cors().and().csrf().disable().antMatcher("/**").
                authorizeRequests().antMatchers("/", "/index.html","/token/*", 
                		"/v2/**",
                        "/configuration/**",
                        "/migration/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                
                //** BOTH ADMIN/USER CAN ACCESS **//*
                //** ONLY ADMIN CAN ACCESS **//*
                .anyRequest().authenticated().and().addFilterBefore(googleIDTokenValidationFilter,UsernamePasswordAuthenticationFilter.class);
	    	}else{
	    		logger.info("Security Disabled");
	    		
	    		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
	    	}
	        
	                
	    }
	    
	  

}