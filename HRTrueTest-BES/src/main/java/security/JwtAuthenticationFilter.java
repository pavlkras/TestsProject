package main.java.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import main.java.security.exceptions.JwtTokenMissingException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter { 
	
	public static final String HEADER_USER_ID = "Authorized-User";
	
	public JwtAuthenticationFilter() {
        super("/**");
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

        String authToken = header.split(" ")[1];

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        
        return getAuthenticationManager().authenticate(authRequest);
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request){

        	private final String userId = (String) authResult.getPrincipal();
        	
			@Override
			public String getHeader(String name) {				
				if (HEADER_USER_ID.equals(name)) {
					return userId;
				}
				
				return super.getHeader(name);
			}

			@Override
			public Enumeration<String> getHeaderNames() {
				List<String> list = Collections.list(((HttpServletRequest)getRequest()).getHeaderNames());
				list.add(HEADER_USER_ID);

				Enumeration<String> en = Collections.enumeration(list);
				return en;
			}
        	
        };
        chain.doFilter(wrapper, response);
    }

}
