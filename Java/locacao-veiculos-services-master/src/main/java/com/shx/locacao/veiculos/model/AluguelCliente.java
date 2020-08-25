package com.shx.locacao.veiculos.model;

public class AluguelCliente extends Aluguel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 830306991556806839L;
	
	private String nome_cliente;

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public AluguelCliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AluguelCliente [nome_cliente=" + nome_cliente + "]";
	}
}
