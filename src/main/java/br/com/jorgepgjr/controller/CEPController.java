package br.com.jorgepgjr.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jorgepgjr.bo.CEPBO;
import br.com.jorgepgjr.entity.Endereco;
import br.com.jorgepgjr.entity.ErrorInfo;
import br.com.jorgepgjr.exception.CEPGenericException;

/**
 * REST Controller used for CEP operations
 * 
 * @author jorge
 *
 */
@RestController
public class CEPController {

	@Resource
	private CEPBO cepBO;

	/**
	 * Search for an address for the CEP number
	 * @param cep number
	 * @return {@linkplain Endereco}
	 */
	@RequestMapping(value = "/cep/{cep}", method = RequestMethod.GET)
	public HttpEntity<Endereco> findCep(@PathVariable(value = "cep") String cep) {
		
		if (StringUtils.isEmpty(cep)) {
			throw new CEPGenericException("CEP informado é invalido");
		}

		Endereco endereco = cepBO.findCEP(cep);
		endereco.add(linkTo(methodOn(CEPController.class).findCep(cep))
				.withSelfRel());
		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
	}

	@RequestMapping(value = "/cep", method = RequestMethod.GET)
	public HttpEntity<ErrorInfo> rootCep(HttpServletRequest request) {

		ErrorInfo error = new ErrorInfo(request.getRequestURL().toString(),
				new CEPGenericException("Tem certeza que a URL está certa?"));
		error.add(linkTo(methodOn(CEPController.class).findCep("11055341")).withRel("search"));
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.UNAUTHORIZED);
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
