package main.java.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import main.java.utils.UserLoaderService;

public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserLoaderService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return service.loadUserByUsername(username);
	}

}
