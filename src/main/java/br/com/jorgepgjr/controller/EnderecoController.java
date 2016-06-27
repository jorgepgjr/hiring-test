package br.com.jorgepgjr.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jorgepgjr.bo.CEPBO;
import br.com.jorgepgjr.bo.EnderecoBO;
import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.entity.ErrorInfo;
import br.com.jorgepgjr.exception.CEPGenericException;
import br.com.jorgepgjr.exception.EnderecoGenericException;
import br.com.jorgepgjr.exception.EnderecoUpdateWithPostException;
import br.com.jorgepgjr.repository.EnderecoRepository;

/**
 * REST Controller used for {@linkplain Endereco}} operations
 * 
 * @author jorge
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Resource
	private CEPBO cepBO;
	
	@Resource
	private EnderecoRepository enderecoRepository;
	
	@Resource
	private EnderecoBO enderecoBO;

	private static final Logger LOG = LoggerFactory.getLogger(EnderecoController.class);

	/**
	 * Returns a list of all Enderecos stored in DataBase
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<Iterable<Endereco>> findAllEndereco() {
		LOG.info("Searching for all Adress at DataBase");
		Iterable<Endereco> enderecos = enderecoRepository.findAll();
		enderecos.forEach(s -> this.createHateoasLinks(s));
		return new ResponseEntity<Iterable<Endereco>>(enderecos, HttpStatus.OK);
	}

	/**
	 * Find {@link Endereco} for the given code
	 * @param cod
	 * @return
	 */
	@RequestMapping(value = "/{cod}", method = RequestMethod.GET)
	public HttpEntity<Endereco> findEndereco(@PathVariable(value = "cod") Long cod) {
		LOG.info("Searching Address code={}",cod);
		Endereco endereco = enderecoRepository.findOne(cod);
		if (endereco != null) {
			this.createHateoasLinks(endereco);
			return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);			
		}
		return new ResponseEntity<Endereco>(endereco, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Delete {@linkplain Endereco} for the given code
	 * @param cod
	 * @return
	 */
	@RequestMapping(value = "/{cod}", method = RequestMethod.DELETE)
	public HttpEntity<Endereco> deleteEndereco(@PathVariable(value = "cod") Long cod) {
		enderecoBO.delete(cod);
		return new ResponseEntity<Endereco>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Update {@linkplain Endereco} for the given code
	 * @param cod
	 * @param endereco
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/{cod}", method = RequestMethod.PUT)
	public HttpEntity<Endereco> updateEndereco(@PathVariable(value = "cod") Long cod, @Valid @RequestBody Endereco endereco, BindingResult result) {
		endereco.setCd(cod);
		if (result.hasErrors()) {
			throw new EnderecoGenericException("CEP, Logradouro, Número, Cidade e Estado são campos obrigatórios");
		}
		Endereco persitedEndereco = enderecoBO.update(endereco);
		this.createHateoasLinks(persitedEndereco);
		return new ResponseEntity<Endereco>(persitedEndereco, HttpStatus.OK);
	}
	
	/**
	 * Insert new {@linkplain Endereco}
	 * @param endereco
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Endereco> insertEndereco(@Valid @RequestBody Endereco endereco, BindingResult result) {
		if (endereco != null && endereco.getCd() != null ) {
			LOG.info("Trying to update on a POST Method");
			throw new EnderecoUpdateWithPostException("Não permitido inserção de um endereco passando o código, use PUT para atualizar", endereco);
		}
		if (result.hasErrors()) {
			throw new EnderecoGenericException("CEP, Logradouro, Número, Cidade e Estado são campos obrigatórios");
		}
		
		Endereco persitedEndereco = enderecoBO.insert(endereco);
		this.createHateoasLinks(persitedEndereco);
		return new ResponseEntity<Endereco>(persitedEndereco, HttpStatus.CREATED);
	}
	

	/**
	 * Creates HATEOAS generic links
	 * @param endereco
	 */
	private void createHateoasLinks(Endereco endereco){
		endereco.add(linkTo(methodOn(EnderecoController.class).findAllEndereco()).withRel("search-all"));
		endereco.add(linkTo(methodOn(EnderecoController.class).findEndereco(endereco.getCd())).withRel("search"));
		endereco.add(linkTo(EnderecoController.class).slash(endereco.getCd()).withRel("insert"));
		endereco.add(linkTo(EnderecoController.class).slash(endereco.getCd()).withRel("update"));
		endereco.add(linkTo(methodOn(EnderecoController.class).deleteEndereco(endereco.getCd())).withRel("delete"));
	}

	/**
	 * Generic Error Handler
	 * @param req
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(EnderecoGenericException.class)
	@ResponseBody
	public ErrorInfo badRequestErrorHandler(HttpServletRequest req, Exception ex) {
		return new ErrorInfo(req.getRequestURL().toString(), ex);
	}
	
	/**
	 * Trying to update an Endereco  with POST method Error Handler
	 * @param req
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(EnderecoUpdateWithPostException.class)
	@ResponseBody
	public ErrorInfo enderecoUpdateWithPostErrorHandler(HttpServletRequest req, EnderecoUpdateWithPostException ex) {
		ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL().toString(), ex);
		errorInfo.add(linkTo(EnderecoController.class).slash(ex.getEndereco().getCd()).withRel("update"));
		return errorInfo;
	}
	
	/**
	 * Trying to delete a non existing value
	 * @param req
	 * @param ex
	 * @param cd
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseBody
	public ErrorInfo deleteNotFoundErrorHandler(HttpServletRequest req, Exception ex, Long cd) {
		LOG.info("No register fond for code {}",cd);
		return new ErrorInfo(req.getRequestURL().toString(), cd.toString());
	}
	
	/**
	 * Generic CEP Error Handler
	 * @param req
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(CEPGenericException.class)
	@ResponseBody
	public ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
		return new ErrorInfo(req.getRequestURL().toString(), ex);
	}
}
