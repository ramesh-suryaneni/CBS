

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
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webhooks/**", "/v2/**", "/configuration/**", "/swagger-resources/**",
				"/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		logger.info("Security enabled");

		if (securityEnabled) {

			http.cors().and().csrf().disable().authorizeRequests()

					/** ALL USER CAN ACCESS **/
					.antMatchers("/bookings/**", "/contractors/**", "/contractor_employees/**",
							"/supplier_location_types/**",

							"/disciplines/**", "/regions/**", "/tax_rates/**",

							"/macanomy/**", "/recruiting_reasons/**", "/roles/**", "/supplier_types/**",
							"/currencies/**")
					.authenticated()

					/** ONLY ADMIN CAN ACCESS **/
					/*
					 * .antMatchers(HttpMethod.GET,
					 * "/bookings/**").authenticated()
					 * 
					 * //Bookings can be created either Creator, HR or Admin
					 * users .antMatchers(HttpMethod.POST,
					 * "/bookings").hasAnyRole(SecurityConstants.
					 * ROLE_BOOKING_CREATOR,
					 * SecurityConstants.ROLE_CONTRACT_MGT,
					 * SecurityConstants.ROLE_PO_MGT,
					 * SecurityConstants.ROLE_ADMIN)
					 * .antMatchers(HttpMethod.PUT,
					 * "/bookings/**").hasAnyRole(SecurityConstants.
					 * ROLE_BOOKING_CREATOR,
					 * SecurityConstants.ROLE_CONTRACT_MGT,
					 * SecurityConstants.ROLE_PO_MGT,
					 * SecurityConstants.ROLE_ADMIN)
					 * .antMatchers(HttpMethod.PATCH,
					 * "/bookings/**").hasAnyRole(SecurityConstants.
					 * ROLE_BOOKING_CREATOR,
					 * SecurityConstants.ROLE_CONTRACT_MGT,
					 * SecurityConstants.ROLE_PO_MGT,
					 * SecurityConstants.ROLE_ADMIN)
					 * .antMatchers(HttpMethod.DELETE,
					 * "/bookings/**").hasAnyRole(SecurityConstants.
					 * ROLE_BOOKING_CREATOR,
					 * SecurityConstants.ROLE_CONTRACT_MGT,
					 * SecurityConstants.ROLE_PO_MGT,
					 * SecurityConstants.ROLE_ADMIN)
					 */

					.anyRequest().authenticated().and().exceptionHandling()
					.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			http.addFilterBefore(googleIDTokenValidationFilter, UsernamePasswordAuthenticationFilter.class);

		} else {

			logger.info("Security Disabled");
			http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
		}

	}

}
