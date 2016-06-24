package br.com.jorgepgjr.bo;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.jorgepgjr.HiringTestApplication;
import br.com.jorgepgjr.entity.Endereco;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HiringTestApplication.class)
public class CEPBOIntegrationTest {

	@Resource
	private CEPBO cepbo;

	@Test
	public void callCEPService() {
		Endereco endereco = cepbo.findCEP("11055341");
		System.out.println(endereco);
		Assert.assertTrue("Gonzaga".equals(endereco.getBairro()));
	}
}
