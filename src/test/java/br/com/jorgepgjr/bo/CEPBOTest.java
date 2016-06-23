package br.com.jorgepgjr.bo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.jorgepgjr.entity.Endereco;

@RunWith(MockitoJUnitRunner.class)
public class CEPBOTest {

	@InjectMocks
	private CEPBO cepbo;
	
	//TODO:change to a real test
	@Test
	public void simpleTest(){
		Endereco endereco = cepbo.findCEP("1105341");
		Assert.assertTrue("Santos".equals(endereco.getCidade()));
	}
}
