package com.realnet.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.realnet.logging.NoLogging;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider implements Serializable {
//	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// get subject (sub) from token
	@NoLogging
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	@NoLogging
	public String getEmailFromToken(String token) {
		String email = null;
		try {
			Claims claims = getAllClaimsFromToken(token);
			email = claims.getSubject();
		} catch (Exception e) {
			email = null;
		}
		// logger.info("email = {} ", email);
		return email;
	}

	@NoLogging
	public List<String> getRolesFromToken(String token) {
		List<String> roles = null;
		try {
			Claims claims = getAllClaimsFromToken(token);
			roles = Arrays.asList((String) claims.get(JWTConstant.AUTHORITIES_KEY));
			// logger.info("roles = {} ", roles);
		} catch (Exception e) {
			roles = null;
		}
		return roles;
	}

	// get expiration (exp) from token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	@NoLogging
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	@NoLogging
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(JWTConstant.SECRET_KEY).parseClaimsJws(token).getBody();
	}

	@NoLogging
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	@NoLogging
	public String generateToken(Authentication authentication) {
		final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		// logger.info("authorities = {} ", authorities);
		final List<String> AuthorityList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		//logger.info("authentication.getName() = {} ", authentication.getName());
		//logger.info("Authority List = {}", AuthorityList);
		return Jwts.builder().setSubject(authentication.getName()) // USER NAME OR EMAIL
				.claim(JWTConstant.AUTHORITIES_KEY, authorities) // ROLES
				.signWith(SignatureAlgorithm.HS256, JWTConstant.SECRET_KEY).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWTConstant.ACCESS_TOKEN_VALIDITY_SECONDS * 1000)).compact();
	}

	@NoLogging
	public Boolean validateToken(String token, UserDetails userDetails) {
		// CustomUserDetails user = (CustomUserDetails) userDetails;
		// System.out.println("validateToken -> jwtUser : " + user);
		// return (username.equals(user.getUsername()) && !isTokenExpired(token));

		// final String username = getUsernameFromToken(token);
		final String email = getEmailFromToken(token);
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}

	@NoLogging
	UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {

		final JwtParser jwtParser = Jwts.parser().setSigningKey(JWTConstant.SECRET_KEY);

		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

		final Claims claims = claimsJws.getBody();

		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(JWTConstant.AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

}
