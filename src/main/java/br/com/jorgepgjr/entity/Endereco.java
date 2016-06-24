package br.com.jorgepgjr.entity;

import org.springframework.hateoas.ResourceSupport;

/**
 * This is a simple POJO class that represents an Address
 * 
 * @author jorge
 *
 */
public class Endereco extends ResourceSupport {

	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;

	@Override
	public String toString() {
		return "Endereco [logradouro=" + logradouro + ", complemento=" + complemento + ", bairro=" + bairro
				+ ", localidade=" + localidade + ", uf=" + uf + "]";
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
