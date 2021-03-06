package com.philomath.samples.library.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

	@Value("${auth.token.name}")
	private String tokenName;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		SecurityContext context = SecurityContextHolder.getContext();
		
		// check if header contains auth token
		String authToken = request.getHeader(tokenName);
		
		// if there is an auth token, create an Authentication object
		if (authToken != null)	{
			Authentication auth = new LibraryAuthentication(authToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		// forward the request
		filterChain.doFilter(request, response);
	}
	

}
