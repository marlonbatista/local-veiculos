package com.shx.locacao.veiculos.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Aluguel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4491274637565237456L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cod_aluguel;
	private Integer cod_cliente;
	private Integer cod_veiculo;
	
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar data_locacao;
	
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar data_devolucao;
	
	private boolean status;
	
	private Float valor_total;
	
	public Aluguel() {
	}

	public Integer getCod_aluguel() {
		return cod_aluguel;
	}

	public void setCod_aluguel(Integer cod_aluguel) {
		this.cod_aluguel = cod_aluguel;
	}

	public Integer getCod_cliente() {
		return cod_cliente;
	}

	public void setCod_cliente(Integer cod_cliente) {
		this.cod_cliente = cod_cliente;
	}

	public Integer getCod_veiculo() {
		return cod_veiculo;
	}

	public void setCod_veiculo(Integer cod_veiculo) {
		this.cod_veiculo = cod_veiculo;
	}

	public Calendar getData_locacao() {
		return data_locacao;
	}

	public void setData_locacao(Calendar data_locacao) {
		this.data_locacao = data_locacao;
	}

	public Calendar getData_devolucao() {
		return data_devolucao;
	}

	public void setData_devolucao(Calendar data_devolucao) {
		this.data_devolucao = data_devolucao;
	}


	public Float getValor_total() {
		return valor_total;
	}

	public void setValor_total(Float valor_total) {
		this.valor_total = valor_total;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod_aluguel == null) ? 0 : cod_aluguel.hashCode());
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
		Aluguel other = (Aluguel) obj;
		if (cod_aluguel == null) {
			if (other.cod_aluguel != null)
				return false;
		} else if (!cod_aluguel.equals(other.cod_aluguel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aluguel [cod_aluguel=" + cod_aluguel + ", cod_cliente=" + cod_cliente + ", cod_veiculo=" + cod_veiculo
				+ ", data_locacao=" + data_locacao + ", data_devolucao=" + data_devolucao + ", status=" + status
				+ ", valor_total=" + valor_total + "]";
	}
	
	
}
