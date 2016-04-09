package main.java.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private String token;
	
	public AuthenticatedUser(long id, String username, String token, Collection<? extends GrantedAuthority> authorities) {
		super(username, token, authorities);
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}
