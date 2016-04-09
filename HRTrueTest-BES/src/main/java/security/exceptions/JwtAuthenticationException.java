package main.java.security.exceptions;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public JwtAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
