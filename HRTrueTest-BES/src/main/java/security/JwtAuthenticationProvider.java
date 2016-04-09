package main.java.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import main.java.security.dao.User;
import main.java.security.exceptions.JwtAuthenticationException;
import main.java.security.exceptions.JwtTokenMalformedException;
import main.java.security.util.JwtUtil;

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		 AuthenticationTimeout timeout = AuthenticationTimeout.getInstance();
		 String userName = userDetails.getUsername();
		 
		 if (!timeout.isActive(userName)) {
			 timeout.revokeAccess(userName);
			 throw new JwtAuthenticationException("JWT token session time expired");
		 }
		 
		 timeout.updateTimeout(userName);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

		User parsedUser = jwtUtil.parseToken(token);

		if (parsedUser == null) {
			throw new JwtTokenMalformedException("JWT token is not valid");
		}

		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

		return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorityList);
	}

}
