package com.shx.locacao.veiculos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shx.locacao.veiculos.model.Cliente;
import com.shx.locacao.veiculos.repository.ClienteRepository;

import java.util.List;


@RestController 
@RequestMapping(value="/api/v1/cliente",produces = MediaType.APPLICATION_JSON_VALUE )
public class ClienteController {

	@Autowired
	private ClienteRepository repositorio;
	
	
	@GetMapping(path = "/")
	public List<Cliente> buscarClientes () {
		List<Cliente> clientes = repositorio.findAll();

		return clientes;
	}
	
	@GetMapping(path = {"/{id}"})
	public Cliente findById(@PathVariable(required=true) Integer id){
			return repositorio.findById(id);			
	}
	
}
