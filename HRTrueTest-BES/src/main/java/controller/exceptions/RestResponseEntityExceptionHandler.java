package main.java.controller.exceptions;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import main.java.jsonsupport.ErrorJsonModel;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<Object> handleAccessDeniedException(Exception e, WebRequest request){
		return new ResponseEntity<>(new ErrorJsonModel("access denied"), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<Object> handleBadCredentialsException(Exception e, WebRequest request){
		return new ResponseEntity<>(new ErrorJsonModel(e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler({UsernameNotFoundException.class})
	public ResponseEntity<Object> handleUsernameNotFoundException(Exception e, WebRequest request){
		return new ResponseEntity<>(new ErrorJsonModel(e.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler({PersistenceException.class})
	public ResponseEntity<Object> handlePersistenceExceptionException(Exception e, WebRequest request){
		return new ResponseEntity<>(new ErrorJsonModel(e.getCause().getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
