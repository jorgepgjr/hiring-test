package br.com.jorgepgjr.bo;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.exception.EnderecoGenericException;
import br.com.jorgepgjr.repository.EnderecoRepository;

/**
 * BO to handle {@linkplain Endereco} logics
 * 
 * @author jorge
 *
 */
@Component
public class EnderecoBO {

	private static final Logger LOG = LoggerFactory.getLogger(EnderecoBO.class);
	
	@Resource
	private EnderecoRepository enderecoRepository;
	
	@Resource
	private CEPBO cepBO;
	
	/**
	 * Check to see if the CEP is valid then save in DataBase
	 * @param endereco
	 * @return
	 */
	@Transactional(rollbackOn=Exception.class)
	public Endereco insert(Endereco endereco){
		cepBO.findCEP(endereco.getCep());
		return enderecoRepository.save(endereco);
	}
	
	/**
	 * Check if CEP is valid then if the Endereco exists in the DB, update it
	 * @param endereco
	 * @return
	 */
	@Transactional(rollbackOn=Exception.class)
	public Endereco update(Endereco endereco){
		cepBO.findCEP(endereco.getCep());
		boolean exists = enderecoRepository.exists(endereco.getCd());
		
		if (!exists) {
			LOG.info("Trying to update an Endereco that is not persisted at DB Endereco={}",endereco);
			throw new EnderecoGenericException("Endereço não cadastrado, utilize o Method POST para inserir um novo endereço");
		}
		return enderecoRepository.save(endereco);
	}
	
	/**
	 * Try to delete Endereco
	 * @param cd
	 */
	public void delete(Long cd){
		try {
			enderecoRepository.delete(cd);
		}catch(EmptyResultDataAccessException e){
			throw new EnderecoGenericException("Endereco com código= "+ cd +" não encontrado");
		} catch (Exception e) {
			LOG.error("Error when trying to delete Endereco with code {}",cd,e);
			throw new EnderecoGenericException("Erro inexperado ao tentar deletar endereco",e);
		}
	}
}
