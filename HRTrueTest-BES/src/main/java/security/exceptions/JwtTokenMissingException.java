package main.java.security.exceptions;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtTokenMissingException extends AuthenticationException {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public JwtTokenMissingException(String message) {
		super(message);
	}
}
