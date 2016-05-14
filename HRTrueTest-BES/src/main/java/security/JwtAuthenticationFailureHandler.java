package main.java.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.jsonsupport.ErrorJsonModel;
import main.java.security.exceptions.JwtTokenExpiredException;
import main.java.security.exceptions.JwtTokenMalformedException;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json");
		
		if (exception instanceof JwtTokenExpiredException){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		else if (exception instanceof JwtTokenMalformedException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		PrintWriter writer = response.getWriter();
	    writer.write(new ObjectMapper().writeValueAsString(new ErrorJsonModel(exception.getMessage())));
	    writer.close();
	}

}
