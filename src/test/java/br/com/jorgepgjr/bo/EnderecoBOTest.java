package br.com.jorgepgjr.bo;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.exception.EnderecoGenericException;
import br.com.jorgepgjr.repository.EnderecoRepository;

/**
 * Teting class of {@linkplain EnderecoBO}
 * @author jorge
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EnderecoBOTest {
	
	@InjectMocks
	private EnderecoBO enderecoBO;
	
	@Mock
	private EnderecoRepository enderecoRepository;
	
	@Mock
	private CEPBO cepBO;
	
	private Endereco endereco;
	
	@Before
	public void setUp(){
		this.endereco = new Endereco();
		endereco.setCd(1L);
		endereco.setCep("11055341");
		endereco.setBairro("Gonzaga");
		endereco.setComplemento("apt 13");
	}
	
	/**
	 * Insert Test
	 */
	@Test
	public void testInsert(){
		enderecoBO.insert(endereco);
	}
	
	/**
	 * Update Success Test 
	 */
	@Test
	public void testUpdate(){
		Endereco updatedEndereco = new Endereco();
		updatedEndereco.setBairro("Ponta da Praia");
		when(enderecoRepository.exists(1l)).thenReturn(true);
		when(enderecoRepository.save(endereco)).thenReturn(updatedEndereco);
		Endereco response = enderecoBO.update(endereco);
		Assert.assertEquals(updatedEndereco, response);
	}
	
	/**
	 * Simulating an update a non existing Endereco
	 */
	@Test(expected=EnderecoGenericException.class)
	public void testUpdateException(){
		Endereco updatedEndereco = new Endereco();
		updatedEndereco.setBairro("Ponta da Praia");
		when(enderecoRepository.exists(1l)).thenReturn(false);
		when(enderecoRepository.save(endereco)).thenReturn(updatedEndereco);
		enderecoBO.update(endereco);
	}
	
	/**
	 * Delete Test
	 */
	@Test
	public void testDelete(){
		enderecoBO.delete(1L);
	}
	
	/**
	 * Simulating a Delete a non existing Endereco
	 */
	@Test(expected=EnderecoGenericException.class)
	public void testDeleteException(){
		doThrow(new EmptyResultDataAccessException(1)).when(enderecoRepository).delete(1L);
		enderecoBO.delete(1L);
	}
}
