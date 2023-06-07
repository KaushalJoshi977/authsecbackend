package com.realnet.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.classmate.TypeResolver;
import com.realnet.logging.NoLogging;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
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
public class SwaggerConfig {
	
	@Autowired
	private TypeResolver typeResolver;

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	@NoLogging
	private ApiKey apiKey() {
		// return new ApiKey("Authorization", "", "header"); // <<< === Create a Header
		//return new ApiKey("JWT", "Authorization", "header");
		// (We are createing header named "Authorization" here)
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");

	}

	// This path will be called when swagger is loaded first time to get a token
	/*
	 * @Bean public UiConfiguration uiConfig() { return new
	 * UiConfiguration("session"); }
	 */

	@NoLogging
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	@NoLogging
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

//	ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title("API Reference")
//				.version("1.0.0")
//				.build();
//	}

	@NoLogging
	ApiInfo realItApiInfo() {
		return new ApiInfoBuilder()
				.title("RealIt APIs")
				.version("1.0.0")
				.build();
	}

//	@Bean
//	public Docket customImplementation() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("public")
//				.apiInfo(apiInfo())
//				.securityContexts(Arrays.asList(securityContext()))
//				.securitySchemes(newArrayList(apiKey()))
//				.select()
//				.paths(PathSelectors.any())
//				// .apis(RequestHandlerSelectors.any()) // If you want to list all the apis
//				// including springboots own
//				.apis(RequestHandlerSelectors.basePackage("com.app.api"))
//				.build()
//				.pathMapping("/")
//				.useDefaultResponseMessages(false)
//				.directModelSubstitute(LocalDate.class, String.class)
//				.genericModelSubstitutes(ResponseEntity.class);
//	}

	@NoLogging
	@Bean
	public Docket realItCustomeImplementation() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("realit")
				.apiInfo(realItApiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(newArrayList(apiKey()))
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.realnet")).build().pathMapping("/")
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, newArrayList(
							new ResponseMessageBuilder()   
								.code(500)
							    .message("Internal Server Error").build(),
							    //.responseModel(new ModelRef("Error")).build(),
						    new ResponseMessageBuilder() 
								.code(403)
							    .message("Forbidden!").build(),
						    new ResponseMessageBuilder()
						      	.code(400)
						      	.message("Bad Request").build()
						      	//.responseModel(new ModelRef("Error")).build()
						    ))
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class);
	}

}
