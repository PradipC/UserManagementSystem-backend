package com.user.mgmt.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.user.mgmt.controller")).paths(PathSelectors.any())
				.build()
				.securitySchemes(Arrays.asList(securityScheme()))
	            .securityContexts(Arrays.asList(securityContext()));

	}

	
	private ApiKey securityScheme() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
	
	
	 private SecurityContext securityContext() {
	        return SecurityContext.builder()
	                .securityReferences(Arrays.asList(new SecurityReference("Authorization", new AuthorizationScope[0])))
	                .forPaths(PathSelectors.any())
	                .build();
	    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User Management Application").description("Pardip Choudhari")
				.version("1.0.0").build();
	}

}
