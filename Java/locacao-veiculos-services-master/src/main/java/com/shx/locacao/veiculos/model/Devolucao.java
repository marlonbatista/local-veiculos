package com.shx.locacao.veiculos.model;

import java.io.Serializable;

public class Devolucao extends  AluguelCliente implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644982654772631316L;
	
	
	private String nome_veiculo;
	
	public String getNome_veiculo() {
		return nome_veiculo;
	}
	
	public void setNome_veiculo(String nome_veiculo) {
		this.nome_veiculo = nome_veiculo;
	}
	
	public Devolucao() {
		super();
	}
	
	@Override
	public String toString() {
		return "Devolucao [nome_veiculo=" + nome_veiculo + "]";
	}
}
