/**
 * 
 *//*
package com.imagination.cbs.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


*//**
 * @author Ramesh.Suryaneni
 *
 *//*
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
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
	                authorizeRequests()
	                
	                *//** BOTH ADMIN/USER CAN ACCESS **//*
	                *//** ONLY ADMIN CAN ACCESS **//*
	                .anyRequest().authenticated();
	                
	    }

}
*/