/*package com.imagination.cbs.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*//**
 * @author Ramesh.Suryaneni
 *
 *//*
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final Contact DEFAULT_CONTACT = new Contact(
		      "Ramesh Suryaneni", "http://cbs.imagination.com", "ramesh.suryaneni@yash.com");

	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
		      "Contractor Booking System", "Contractor Booking System api", "1.0",
		      "", DEFAULT_CONTACT, 
		      "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
		  
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
        		.securitySchemes(Arrays.asList(apiKey()))
                //.securityContexts(Collections.singletonList(securityContext()))
        		.apiInfo(DEFAULT_API_INFO)
        		.select()
        		//.apis(RequestHandlerSelectors.any())
        		.apis(RequestHandlerSelectors.basePackage("com.imagination.cbs"))
        		.paths(PathSelectors.any())
        		.build();                                           
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
      }

    private List<SecurityReference> defaultAuth() {
      final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
      final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
      return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }

    private ApiKey apiKey() {
      return new ApiKey("Bearer", "Authorization", "header");
    } 
}
*/