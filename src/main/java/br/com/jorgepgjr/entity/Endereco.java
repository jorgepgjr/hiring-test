package br.com.jorgepgjr.entity;

import org.springframework.hateoas.ResourceSupport;

/**
 * This is a simple POJO class that represents an Address
 * @author jorge
 *
 */
public class Endereco extends ResourceSupport{

	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
