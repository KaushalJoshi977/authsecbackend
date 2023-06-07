package com.realnet.config;

public class JWTConstant {

	// private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10
		// days Validity
		public static final long VALIDITY_TIME_MS = 2 * 60 * 60 * 1000; // 2 hours validity

		public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
		public static final String SECRET_KEY = "realnet";
		public static final String TOKEN_PREFIX = "Bearer ";
		public static final String HEADER_STRING = "Authorization";
		public static final String AUTHORITIES_KEY = "scopes";

		
		public static final String JWT_ILLEGAL_ARGUMENT_MESSAGE = "An error occured during getting username from token";
		public static final String JWT_EXPIRED_MESSAGE = "The token is expired and not valid anymore";
		public static final String JWT_SIGNATURE_MESSAGE = "Authentication Failed. Username or Password not valid.";
		public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
		public static final String FORBIDDEN_MESSAGE = "You don't have the right to access to this resource";
		public static final String INVALID_DATA_MESSAGE = "One or many parameters in the request's body are invalid";
}
