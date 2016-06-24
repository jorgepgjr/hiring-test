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

	private static final int CEP_MAX_LENGTH = 8;

	@Autowired
	private RestTemplate restTemplate;

	String CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

	public Endereco findCEP(String cep){
		int count = 0;
		Endereco endereco = null;
		
		while (endereco == null || count != 3) {
			endereco = this.callFindCEPService(cep);
			count ++;
//			cep = (cep.substring(0, cep.length() - count)+"000").substring(0, CEP_MAX_LENGTH);
		}
		return endereco;
	}
	
	private Endereco callFindCEPService(String cep) {
		Endereco endereco;
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
