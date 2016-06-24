package br.com.jorgepgjr.bo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.exception.CEPGenericException;

/**
 * BO to handle CEP search
 * 
 * @author jorge
 *
 */
@Component
public class CEPBO {

	@Autowired
	private RestTemplate restTemplate;

	String CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

	public Endereco findCEP(String cep) {
		Endereco endereco = new Endereco();
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("cep", cep);
		try {
			endereco = restTemplate.getForObject(CEP_URL, Endereco.class, variables);
		} catch (HttpStatusCodeException exception) {
			throw new CEPGenericException("CEP informado é invalido");
		}
		return endereco;
	}
}
