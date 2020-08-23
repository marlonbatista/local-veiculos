package com.shx.locacao.veiculos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shx.locacao.veiculos.model.Veiculo;
import com.shx.locacao.veiculos.repository.VeiculoRepository;

@RestController 
@RequestMapping(value="/api/v1/veiculo", produces = MediaType.APPLICATION_JSON_VALUE )
public class VeiculoController {
	
	@Autowired
	private VeiculoRepository repositorio;
	
	@GetMapping(path = "/")
	public List<Veiculo> buscarClientes () {
		List<Veiculo> clientes = repositorio.findAll();

		return clientes;
	}
	
	@GetMapping(path = {"/{id}"})
	public Veiculo findById(@PathVariable(required=true) Integer id){
			return repositorio.findById(id);			
	}
}
