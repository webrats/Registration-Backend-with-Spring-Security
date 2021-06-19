package com.mvc.reg.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil ;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
			String jwtString = extractJwtFromRequest(request);
			
			try {
				if(StringUtils.hasText(jwtString) && jwtUtil.validateToken(jwtString) ) {
					UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtString), "", jwtUtil.getRolesFromToken(jwtString));
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					// After setting the Authentication in the context, we specify.
					// that the current user is authenticated. So it passes the.
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				else {
					System.out.println("Cannot set the Security Context");
				}
			} catch (ExpiredJwtException ex) {
				request.setAttribute("exception", ex);
				throw ex;
			} catch (BadCredentialsException ex) {
				request.setAttribute("exception", ex);
				throw ex;
			} catch (Exception ex) {
				
				request.setAttribute("exception", ex);
				
			}
			filterChain.doFilter(request, response);
	}

	private String extractJwtFromRequest(HttpServletRequest request) {
		 String bearerToken = request.getHeader("Authorization");
		 if(StringUtils.hasText(bearerToken) && bearerToken.contains("Bearer ")) {
			 return bearerToken.substring(7,bearerToken.length());
		 }
		 return null ;
		
	}

}
