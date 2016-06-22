package br.com.jorgepgjr.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jorgepgjr.entity.Client;

/**
 * Example Controller on how to implement HATEOAS with Spring
 * 
 * @author jorge
 *
 */
@RestController
public class ClientController {

	private static final String TEMPLATE = "Hello, %s!";
	
	@RequestMapping("/client")
	public HttpEntity<Client> showClient(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		Client client = new Client(String.format(TEMPLATE, name));
		client.add(linkTo(methodOn(ClientController.class).showClient(name)).withSelfRel());
        return new ResponseEntity<Client>(client, HttpStatus.OK);
	}
}
