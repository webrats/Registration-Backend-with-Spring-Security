package com.mvc.reg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mvc.reg.model.UserDao;
import com.mvc.reg.model.UserDto;
import com.mvc.reg.repository.UserRepository;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired UserRepository userRepository ;
	
	@Autowired PasswordEncoder bcriptEncoder ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<SimpleGrantedAuthority > role = null ;
		UserDao user = userRepository.findByUsername(username);
		
		if(user!= null) {
			role = Arrays.asList(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
			return new User(user.getUsername(),user.getPassword(),role);
		}
		
		throw new UsernameNotFoundException("User Name Not Found "+username);
	}
	
	
	public UserDao save(UserDto user) throws Exception {
		UserDao newUser = new UserDao();
		
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcriptEncoder.encode( user.getPassword()));
		newUser.setRole(user.getRole());
		
			
			return userRepository.save(newUser);
		
		
	}
	

}
