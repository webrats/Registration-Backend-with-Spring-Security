package com.mvc.reg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mvc.reg.config.CustomUserDetailsService;
import com.mvc.reg.config.JwtUtil;
import com.mvc.reg.model.AuthenticationRequest;
import com.mvc.reg.model.AuthenticationResponse;
import com.mvc.reg.model.UserDto;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/auth/login","http://localhost:4200"})
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token  = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(token,userDetails.getUsername(),1800000));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDto user)throws Exception{
		try {
			
		return ResponseEntity.ok(customUserDetailsService.save(user)) ;
				 
		}catch(Exception e) {
	       
	        return ResponseEntity
	            .status(HttpStatus.ALREADY_REPORTED)
	            .body("Error Message");
	    }
	}
}
