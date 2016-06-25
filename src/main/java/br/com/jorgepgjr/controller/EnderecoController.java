package br.com.jorgepgjr.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.entity.ErrorInfo;
import br.com.jorgepgjr.exception.CEPGenericException;
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
	private static final Logger LOG = LoggerFactory.getLogger(EnderecoController.class);
	
	@RequestMapping(value = "/example", method = RequestMethod.GET)
	public HttpEntity<Endereco> exampleEndereco() {
		Endereco endereco = cepBO.findCEP("11055341");
		endereco.setCd(999L);
		this.createHateoasLinks(endereco);
		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<Iterable<Endereco>> findAllEndereco() {
		Iterable<Endereco> enderecos = enderecoRepository.findAll();
		return new ResponseEntity<Iterable<Endereco>>(enderecos, HttpStatus.OK);
	}

	@RequestMapping(value = "/{cod}", method = RequestMethod.GET)
	public HttpEntity<Endereco> findEndereco(@PathVariable(value = "cod") Long cod) {
		Endereco endereco = enderecoRepository.findOne(cod);
		this.createHateoasLinks(endereco);
		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{cod}", method = RequestMethod.DELETE)
	public HttpEntity<Endereco> deleteEndereco(@PathVariable(value = "cod") Long cod) {
		enderecoRepository.delete(cod);
		return new ResponseEntity<Endereco>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{cod}", method = RequestMethod.PUT)
	public HttpEntity<Endereco> updateEndereco(@PathVariable(value = "cod") Long cod, Endereco endereco) {
		this.createHateoasLinks(endereco);
		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Endereco> insertEndereco(@RequestBody Endereco endereco, BindingResult result) {
		if (endereco != null && endereco.getCd() != null ) {
			LOG.info("Trying to update on a POST Method");
			//TODO: Return error and show update URL
		}
		Endereco savedEndereco = enderecoRepository.save(endereco);
		this.createHateoasLinks(savedEndereco);
		return new ResponseEntity<Endereco>(savedEndereco, HttpStatus.CREATED);
	}
	

	private void createHateoasLinks(Endereco endereco){
		endereco.add(linkTo(methodOn(EnderecoController.class).findAllEndereco()).withRel("search-all"));
		endereco.add(linkTo(methodOn(EnderecoController.class).findEndereco(endereco.getCd())).withRel("search"));
		
//		endereco.add(linkTo(methodOn(EnderecoController.class).insertEndereco(endereco)).withRel("insert"));
		
		endereco.add(linkTo(methodOn(EnderecoController.class).updateEndereco(endereco.getCd(),endereco)).withRel("update"));
		endereco.add(linkTo(methodOn(EnderecoController.class).deleteEndereco(endereco.getCd())).withRel("delete"));
	}
	/**
	 * ExceptionHandler of CEPGeneriException
	 * 
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
