package br.com.jorgepgjr.bo;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.exception.CEPGenericException;

/**
 * JUnit class test of CEPBO
 * @author jorge
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CEPBOTest {

	private static final String CEP = "1105341";

	private final String CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

	@InjectMocks
	private CEPBO cepbo;

	@Mock
	private RestTemplate restTemplate;

	private Endereco endereco;
	@Before
	public void preapre(){
		this.endereco = new Endereco();
		endereco.setCep(CEP);
	}
	/**
	 * Simple sucess test
	 */
	@Test
	public void findCEPSucessTest() {
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("cep", CEP);

		when(restTemplate.getForObject(CEP_URL, Endereco.class, variables)).thenReturn(endereco);
		Endereco response = cepbo.findCEP(CEP);
		Assert.assertTrue(endereco.equals(response));
	}
	
	/**
	 * Simple sucess test
	 */
	@Test
	@Ignore //TODO: adjust
	public void findCEPGenericLogicTest() {
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("cep", CEP);
		when(restTemplate.getForObject(CEP_URL, Endereco.class, variables)).thenReturn(null);
		
		Map<String, String> variables2 = new HashMap<String, String>();
		variables2.put("cep", "11055000");
		when(restTemplate.getForObject(CEP_URL, Endereco.class, variables2)).thenReturn(endereco);
		
		Endereco response = cepbo.findCEP(CEP);
		Assert.assertTrue(endereco.equals(response));
	}

	/**
	 * Testing exception on service response
	 */
	@Test(expected=CEPGenericException.class)	
	public void findCEPExceptionTest() {
		String cep = CEP;
		Endereco endereco = new Endereco();

		Map<String, String> variables = new HashMap<String, String>();
		variables.put("cep", cep);

		when(restTemplate.getForObject(CEP_URL, Endereco.class, variables))
				.thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		Endereco response = cepbo.findCEP(cep);
		Assert.assertTrue(endereco.equals(response));
	}

}
