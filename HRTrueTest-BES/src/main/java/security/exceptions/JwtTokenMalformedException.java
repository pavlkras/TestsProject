package main.java.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtTokenMalformedException extends AuthenticationException {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public JwtTokenMalformedException(String message) {
		super(message);
	}
}
