package com.mvc.reg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private String token ;
	private String email ;
	private int expirationDateInMs ; 
	
	public AuthenticationResponse() {
		
	}
	
	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}
	

	
	public AuthenticationResponse(String token, String email, int expirationDateInMs) {
		super();
		this.token = token;
		this.email = email;
		this.expirationDateInMs = expirationDateInMs;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExpirationDateInMs() {
		return expirationDateInMs;
	}

	public void setExpirationDateInMs(int expirationDateInMs) {
		this.expirationDateInMs = expirationDateInMs;
	}
	
	
	
}
