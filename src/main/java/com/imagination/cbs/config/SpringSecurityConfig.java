
package com.imagination.cbs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.imagination.cbs.security.GoogleAuthenticationEntryPoint;
import com.imagination.cbs.security.GoogleIDTokenValidationFilter;
import com.imagination.cbs.util.SecurityConstants;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${cbssecurity.enabled:true}")
	private boolean securityEnabled;

	@Autowired
	private GoogleIDTokenValidationFilter googleIDTokenValidationFilter;

	@Autowired
	private GoogleAuthenticationEntryPoint unauthorizedHandler;

	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }
	
	@Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
        .antMatchers("/webhooks/**", 
        		"/v2/**",
                "/configuration/**",
                "/migration/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		logger.info("Security enabled");
		
		http.cors().and().csrf().disable().
        authorizeRequests()

		/** ALL USER CAN ACCESS **/
        .antMatchers("/bookings/**", "/contractors/**", "/countries/**", "/disciplines/**", 
        		"/macanomy/**","/recruiting/**", "/roles/**", "/suppliers/**").authenticated()
        
        /** ONLY ADMIN CAN ACCESS **/
        //.antMatchers("/registraton/*").hasRole(SecurityConstants.ROLE_ADMIN_WITHOUT_PREFIX)

        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
            .addFilterBefore(googleIDTokenValidationFilter, UsernamePasswordAuthenticationFilter.class);

		/*
		 * } else {
		 * 
		 * logger.info("Security Disabled");
		 * 
		 * http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").
		 * permitAll(); }
		 */

	}

}