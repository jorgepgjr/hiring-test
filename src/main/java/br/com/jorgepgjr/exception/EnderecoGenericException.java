package br.com.jorgepgjr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class EnderecoGenericException extends RuntimeException{
	private static final long serialVersionUID = -8004182998136070613L;

	public EnderecoGenericException(String message) {
		super(message);
	}
	
	public EnderecoGenericException(String message, Throwable cause) {
		super(message, cause);
	}	
}
