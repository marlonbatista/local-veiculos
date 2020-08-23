package com.shx.locacao.veiculos.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Veiculo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 54917500033022633L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cod_veiculo;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 30)
	private String marca;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 100)
	private String nome;
	
	@NotNull
	@Min(2000)
	@Max(2100)
	private Integer ano;
	
	@NotNull
	@Min(2000)
	@Max(2100)
	private Integer modelo;
	
	private EnumCombustivel combustivel;
	
	@NotNull
	private float valor_diaria;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod_veiculo == null) ? 0 : cod_veiculo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (cod_veiculo == null) {
			if (other.cod_veiculo != null)
				return false;
		} else if (!cod_veiculo.equals(other.cod_veiculo))
			return false;
		return true;
	}
	
	public Integer getCod_veiculo() {
		return cod_veiculo;
	}
	
	public void setCod_veiculo(Integer cod_veiculo) {
		this.cod_veiculo = cod_veiculo;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getAno() {
		return ano;
	}
	
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Integer getModelo() {
		return this.modelo;
	}
	
	public void setModelo(Integer modelo) {
		this.modelo = modelo;
	}
	
	public EnumCombustivel getCombustivel() {
		return combustivel;
	}
	
	public void setCombustivel(EnumCombustivel combustivel) {
		this.combustivel = combustivel;
	}
	
	public float getValor_diaria() {
		return valor_diaria;
	}
	
	public void setValor_diaria(float valor_diaria) {
		this.valor_diaria = valor_diaria;
	}
	
	public Veiculo() {
	}
	
	@Override
	public String toString() {
		return "Veiculo [cod_veiculo=" + cod_veiculo + ", marca=" + marca + ", nome=" + nome + ", ano=" + ano
				+ ", modelo=" + modelo + ", combustivel=" + combustivel + ", valor_diaria=" + valor_diaria + "]";
	}
	
	
}
