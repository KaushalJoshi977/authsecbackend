package com.realnet.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.realnet.logging.NoLogging;


/*
 * @EnableGlobalMethodSecurity annotation is what enables the @PreAuthorize annotation.
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Value("${oauth.resource:http://sercverUsingOAuth2}")
//	  private String baseUrl;
//
//	  @Value("${oauth.authorize:http://sercverUsingOAuth2/rest/oauth/token}")
//	  private String authorizeUrl;
//
//	  @Value("${oauth.token:http://sercverUsingOAuth2/rest/oauth/token}")
//	  private String tokenUrl;


	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	
	/////

	public void configure12(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

	 
//	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.authenticationProvider(userAuthenticationProvider);
//	    }
////	 
//	 
//	 
//	    protected void configure(HttpSecurity http) throws Exception {
//	        http
//	            .cors() // <-- This let it use "corsConfigurationSource" bean.
//	                .and()
//	            .authorizeRequests()
//	                .anyRequest().authenticated()
//	                .and()
//	            
//	    }

	    @Bean
	    protected CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();

	        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
	        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));

	        // NOTE: setAllowCredentials(true) is important,
	        // otherwise, the value of the 'Access-Control-Allow-Origin' header in the response
	        // must not be the wildcard '*' when the request's credentials mode is 'include'.
	        configuration.setAllowCredentials(true);

	        // NOTE: setAllowedHeaders is important!
	        // Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
	        configuration.setAllowedHeaders(Arrays.asList(
	                "Authorization",
	                "Accept",
	                "Cache-Control",
	                "Content-Type",
	                "Origin",
	                "ajax", // <-- This is needed for jQuery's ajax request.
	                "x-csrf-token",
	                "x-requested-with"
	        ));

	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	
	    
	    protected void configure123(HttpSecurity http) throws Exception
	    {
	         http
	        .csrf().disable()
	        .authorizeRequests()
	          .antMatchers("/resources/**").permitAll()
	          .anyRequest().authenticated()
	        .and()
	        .formLogin()
	        .and()
	        .httpBasic()
	        .and()
	        .oauth2Login()
	        .and()
	        .cors();
	        
	    }
	    
	//////
	
	
	
	
	@NoLogging
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@NoLogging	
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@NoLogging
	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	@NoLogging
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Filters will not get executed for the resources
		web.ignoring().antMatchers("/", "/resources/**", "/static/**", "/public/**", "/webui/**", "/h2-console/**",
				"/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/api-docs",
				"/api-docs/**", "/v2/api-docs/**", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png",
				"/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.otf");
	}

	// If Security is not working check application.properties if it is set to
	// ignore
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.exceptionHandling().and().anonymous().and()
//				// Disable Cross site references
//				.csrf().disable()
//				// Add CORS Filter
//				.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
//				// Custom Token based authentication based on the header previously given to the
//				// client
//				.addFilterBefore(new VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
//				// custom JSON based authentication by POST of
//				// {"username":"<name>","password":"<password>"} which sets the token header
//				// upon authentication
//				.addFilterBefore(new GenerateTokenForUserFilter("/session", authenticationManager(), tokenUtil),
//						UsernamePasswordAuthenticationFilter.class)
//				.authorizeRequests()
//				//.antMatchers("/api/instructors/**").hasRole("ADMIN")
//				//.antMatchers("/api/customers/**").hasRole(role)
//				.anyRequest().authenticated();
//	}

	@NoLogging
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				// Add CORS Filter //http.cors().and().csrf().disable().
				.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class).authorizeRequests()
				.antMatchers("/token/**").permitAll()
				.antMatchers("/log2/**").permitAll()
				.antMatchers("/api/**").permitAll()
//				 .antMatchers("/SqlworkbenchSqlcont/**").hasRole("ADMIN")			
				 .anyRequest().authenticated()
				.and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	
	
	}

	/*
	 * http.csrf().disable().exceptionHandling().authenticationEntryPoint(
	 * unauthorizedHandler).and()
	 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
	 * and().authorizeRequests() .antMatchers("/token/**",
	 * "/signup").permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	 * .anyRequest().authenticated();
	 * 
	 * http.addFilterBefore(authenticationTokenFilterBean(),
	 * UsernamePasswordAuthenticationFilter.class);
	 */

	/*
	 * If You want to store encoded password in your databases and authenticate user
	 * based on encoded password then uncomment the below method and provde an
	 * encoder
	 */
	@NoLogging
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public CommonsMultipartResolver getCommonsMultipartResolver() {
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(104857600); // 20MB(20971520 Byte) // 100 mb(104,857,600 Byte)
//		multipartResolver.setMaxInMemorySize(1048576); // 1MB
//		return multipartResolver;
//	}
	
//	  @Bean
//	    public FilterRegistrationBean<HttpLoggingFilter> foo(final HttpLoggingFilter filter) {
//	        final FilterRegistrationBean<HttpLoggingFilter> bean = new FilterRegistrationBean<>();
//	        bean.setFilter(filter);
//	    //    bean.addUrlPatterns("");
//	        return bean;
//	    }
	  
	  @Bean
	  public Caffeine caffeineConfig() {
	      return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
	  }
	  @Bean
	  public CacheManager cacheManager(Caffeine caffeine) {
	      CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
	      caffeineCacheManager.setCaffeine(caffeine);
	      return caffeineCacheManager;
	  }
//	  @Bean
//	  public OAuth2RestOperations oauth2RestTemplate() {
//	      AccessTokenRequest atr = new DefaultAccessTokenRequest();
//	      return new OAuth2RestTemplate(resource(),
//	              new DefaultOAuth2ClientContext(atr));
//	  }
//
//	  @Bean
//	  protected OAuth2ProtectedResourceDetails resource() {
//	      AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
//	      resource.setAccessTokenUri("https://oauth2.googleapis.com/token");
//	      resource.setUserAuthorizationUri(authorizeUrl);
//	      resource.setClientId("508783521234-160if49q1ih8mbmkdc1g7km1c5a6hc95.apps.googleusercontent.com");
//	      resource.setClientSecret("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");
//	      resource.setGrantType("authorization_code");
//	      List<String> l = new ArrayList<String>();
//	      l.add("https://www.googleapis.com/auth/gmail.modify");
//	      resource.setScope(l);
//	      return resource;
//	  }

}
