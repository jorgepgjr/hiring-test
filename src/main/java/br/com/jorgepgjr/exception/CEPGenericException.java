package br.com.jorgepgjr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class CEPGenericException extends RuntimeException{
	private static final long serialVersionUID = 6118869996969535998L;

	public CEPGenericException(String message) {
		super(message);
	}

	public CEPGenericException(String message, Throwable cause) {
		super(message, cause);
	}

}
