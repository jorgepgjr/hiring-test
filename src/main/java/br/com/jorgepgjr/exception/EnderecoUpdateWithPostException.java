package br.com.jorgepgjr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.jorgepgjr.entity.Endereco;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class EnderecoUpdateWithPostException extends RuntimeException{
	private static final long serialVersionUID = 5871353335430438621L;

	private Endereco endereco;
	
	public EnderecoUpdateWithPostException(String message) {
		super(message);
	}
	
	public EnderecoUpdateWithPostException(String message, Endereco endereco) {
		super(message);
		this.setEndereco(endereco);
	}
	
	public EnderecoUpdateWithPostException(String message, Throwable cause) {
		super(message, cause);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
