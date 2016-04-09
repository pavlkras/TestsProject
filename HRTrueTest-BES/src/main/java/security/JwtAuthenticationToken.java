package main.java.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private String token;

	public JwtAuthenticationToken(String token) {
		super(null, null);
		this.token = token;
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

}
