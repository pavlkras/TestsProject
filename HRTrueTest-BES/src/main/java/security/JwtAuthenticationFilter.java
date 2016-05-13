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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import main.java.security.dao.JwtUser;
import main.java.security.exceptions.JwtAuthenticationException;
import main.java.security.util.JwtUtil;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter { 
	
	public static final String HEADER_USER_ID = "Authorized-User";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String token = request.getHeader("Authorization").split(" ")[1];
		
		String username = jwtUtil.getUsernameFromToken(token);
		
		if (username == null) 
			throw new JwtAuthenticationException("can't determine user from token");
		
		UsernamePasswordAuthenticationToken authentication = null;
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(token, userDetails)) {
				authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				throw new JwtAuthenticationException("token validation failed");
			}
		}
		
		return authentication;
	}

	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request){

        	private final String userId = ((JwtUser)(authResult.getPrincipal())).getId().toString();
        	
			@Override
			public Enumeration<String> getHeaders(String name) {
				List<String> list = Collections.list(((HttpServletRequest)getRequest()).getHeaders(name));
				if (HEADER_USER_ID.equals(name)) {
					list.add(userId);
				}

				Enumeration<String> en = Collections.enumeration(list);
				return en;
			}

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
        
        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(wrapper, response);    
    }

}
