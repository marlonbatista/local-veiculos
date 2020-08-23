package com.shx.locacao.veiculos.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

@Entity
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2511960368656927906L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cod_cliente;
	
	@NotNull
	@NotEmpty
	@Size(min=11, max=11)
	private String cpf;
	
	@NotNull
	@NotEmpty
	@Size(min=3, max=100)
	private String nome;
	
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar data_nascimento;
	
	@NotNull
	private boolean status;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod_cliente == null) ? 0 : cod_cliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cod_cliente == null) {
			if (other.cod_cliente != null)
				return false;
		} else if (!cod_cliente.equals(other.cod_cliente))
			return false;
		return true;
	}
	
	public Cliente() {
	}

	public Integer getCod_cliente() {
		return cod_cliente;
	}


	public void setCod_cliente(Integer cod_cliente) {
		this.cod_cliente = cod_cliente;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Calendar getData_nascimento() {
		return data_nascimento;
	}


	public void setData_nascimento(Calendar data_nascimento) {
		this.data_nascimento = data_nascimento;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Cliente [cod_cliente=" + cod_cliente + ", cpf=" + cpf + ", nome=" + nome + ", data_nascimento="
				+ data_nascimento + ", status=" + status + "]";
	}

}
