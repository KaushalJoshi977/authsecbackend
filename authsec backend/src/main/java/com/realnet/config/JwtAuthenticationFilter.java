package com.realnet.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.realnet.logging.NoLogging;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	
//////////
	
	protected void configure(HttpSecurity http) throws Exception
	{
	     http
	    .csrf().disable()
	    .authorizeRequests()
	      .antMatchers(HttpMethod.OPTIONS,"/path/to/allow").permitAll()//allow CORS option calls
	      .antMatchers("/resources/**").permitAll()
	      .anyRequest().authenticated()
	    .and()
	    .formLogin()
	    .and()
	    .httpBasic();
	}
	
	
	//////////////
	
	
	
	
	
	@NoLogging
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(JWTConstant.HEADER_STRING);
		//System.out.println("HEADER => {}" + header);
		String username = null;
		String email = null;
		String authToken = null;
		if (header != null && header.startsWith(JWTConstant.TOKEN_PREFIX)) {
			authToken = header.replace(JWTConstant.TOKEN_PREFIX, "");
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
				//logger.info("getting username from token : {}" + username);
				email = jwtTokenUtil.getEmailFromToken(authToken);
				//logger.info("getting email from token : {}" + email);
				//System.out.println("email => {}" + email);
				
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
		} else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}
		//if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken,
						SecurityContextHolder.getContext().getAuthentication(), userDetails);
				// UsernamePasswordAuthenticationToken authentication = new
				// UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new
				// SimpleGrantedAuthority("ROLE_ADMIN")));
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				logger.debug("authenticated user " + email + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(req, res);
	}
}