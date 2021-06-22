package com.mvc.reg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.reg.config.CustomUserDetailsService;
import com.mvc.reg.model.UserDto;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200/auth/login","http://localhost:4200"})
public class UserController {

	@Autowired CustomUserDetailsService customUserDetailsService ;
	
	@GetMapping("/hellouser")
	public String helloUser() {
		return "Hello User !";
	}
	@GetMapping("/helloadmin")
	public String helloAdmin() {
		return "Hello Admin !";
	}
	
}
