package main.java.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import main.java.security.exceptions.JwtAuthenticationException;
import main.java.security.exceptions.JwtTokenMissingException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private Set<String> allowedRoles; 
	
	public JwtAuthenticationFilter() {
        super("/**");
    }
	
	public void setAllowedRoles(Set<String> allowedRoles){
		this.allowedRoles = allowedRoles;
	}
	
	@Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtTokenMissingException("No JWT token found in request headers");
        }

        String authToken = header.substring(7);

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        
        boolean hasRolePermission = false;
        Authentication authentication = getAuthenticationManager().authenticate(authRequest);
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        for (GrantedAuthority ga : roles){
        	if (allowedRoles == null || allowedRoles.contains(ga.getAuthority())){
        		hasRolePermission = true;
        		break;
        	}
        }
        
        if (!hasRolePermission)
        	throw new JwtAuthenticationException("No role permissions");
        
        return authentication;
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }

}
