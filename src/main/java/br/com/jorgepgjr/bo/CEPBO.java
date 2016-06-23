package br.com.jorgepgjr.bo;

import org.springframework.stereotype.Component;

import br.com.jorgepgjr.entity.Endereco;

/**
 * BO to handle CEP search
 * @author jorge
 *
 */
@Component
public class CEPBO {
	
	public Endereco findCEP(String cep){
		Endereco endereco = new Endereco();
		endereco.setCidade("Santos");
		return endereco;
	}
}
