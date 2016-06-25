package br.com.jorgepgjr.bo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
	private static final String CEP_URL = "https://viacep.com.br/ws/{cep}/json/";
	private static final Logger LOG = LoggerFactory.getLogger(CEPBO.class);
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Search for the CEP and return an {@linkplain Endereco}, if this CAP does
	 * not return a valid {@linkplain Endereco} it tries to be more generic and
	 * search again, <br/>
	 * eg: Trying to search for: 12345678<br/>
	 * if the result is null<br/>
	 * Try to search for: 12345670<br/>
	 * 
	 * @param cep
	 * @return Endereco populated or null
	 */
	public Endereco findCEP(String cep) {
		int count = 0;
		Endereco endereco = null;

		endereco = this.callFindCEPService(cep);
		while (!this.validEndereco(endereco) && count != 3) {
			count++;
			cep = (cep.substring(0, cep.length() - count) + "000").substring(0, CEP_MAX_LENGTH);
			endereco = this.callFindCEPService(cep);
		}
		
		if (!this.validEndereco(endereco)) {
			LOG.info("No Address found for CEP {}" , cep);
			throw new CEPGenericException("Nenhum endereço encontrado");
		}
		return endereco;
	}
	
	/**
	 * False if {@linkplain Endereco} is empty
	 * @param endereco
	 * @return
	 */
	private boolean validEndereco(Endereco endereco){
		if (endereco == null || StringUtils.isEmpty(endereco.getCep())){
			return false;			
		}
		return true;
	}

	/**
	 * Call search address by CEP
	 * 
	 * @param cep
	 * @return
	 * @throws CEPGenericException
	 */
	private Endereco callFindCEPService(String cep) throws CEPGenericException {
		Endereco endereco;
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("cep", cep);
		try {
			LOG.info("Calling CEP service...");
			endereco = restTemplate.getForObject(CEP_URL, Endereco.class, variables);
			LOG.info("Address found {} ",endereco);
		} catch (HttpStatusCodeException exception) {
			LOG.info("Error when calling CEP service", exception);
			throw new CEPGenericException("CEP informado é invalido",exception);
		}
		return endereco;
	}
}
