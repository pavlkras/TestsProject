package main.java.security;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import main.java.model.dao.CredentialsData;
import main.java.security.dao.JwtUser;

public class JwtUserFactory {

	
	
	public JwtUserFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static JwtUser create(CredentialsData user) {
		return new JwtUser(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				mapToGrantedAuthorities(user.getAuthorities()),
				true,
				null);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());
	}

}
