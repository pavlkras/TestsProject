package main.java.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import main.java.security.util.JwtUtil;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter { 
	
	public static final String HEADER_USER_ID = "Authorized-User";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		String token = httpRequest.getHeader("Authorization").split(" ")[1];
		
		String username = jwtUtil.getUsernameFromToken(token);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		chain.doFilter(req, res);
	}


	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
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
        
        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        super.successfulAuthentication(wrapper, response, chain, authResult);    
    }

}
