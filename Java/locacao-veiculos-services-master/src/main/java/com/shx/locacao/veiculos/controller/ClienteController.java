package com.shx.locacao.veiculos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shx.locacao.veiculos.model.Cliente;
import com.shx.locacao.veiculos.repository.ClienteRepository;

@CrossOrigin
@RestController 
@RequestMapping(value="/api/v1/cliente",produces = MediaType.APPLICATION_JSON_VALUE )
public class ClienteController {

	@Autowired
	private ClienteRepository repositorio;
	
	
	@GetMapping(path = "/")
	public List<Cliente> buscarClientes () throws Exception {
		List<Cliente> clientes = repositorio.findAll();
		return clientes;
	}
		
	@RequestMapping(path = "/{id}", method= {RequestMethod.GET})
	public Cliente findById(@PathVariable("id") Integer id) throws Exception{
		if(id == null) {
			throw new Exception("Id inválido");
		} 
		return repositorio.findById(id);
	}
	
	@PostMapping(path= "/")
	public Cliente create(@RequestBody Cliente cliente) throws Exception{
		if(this.isValid(cliente) == false) {
			throw new Exception("dados inválidos");	
		}
		return repositorio.save(cliente);	
	}
	
	
	@RequestMapping(value="/{id}", method= {RequestMethod.DELETE})
	 public void delete( @PathVariable("id") Integer id) throws Exception {
		if(id == null) {
			throw new Exception("Id inválido");	
		}
		repositorio.delete(id);
	 }
	
	private boolean isValid(Cliente cliente) {
		if (cliente.getCpf() == null || 
				cliente.getCpf().length() > 11 ||
				cliente.getCpf().length() < 11 ||
				cliente.getCpf().isBlank() ||
				cliente.getCpf().isEmpty()
				) {
		      return false;
		    }
		    if (cliente.getNome() == null || 
		    		cliente.getNome().isEmpty() || 
		    		cliente.getNome().isBlank() ||
		    		cliente.getNome().length() > 100) {
		      return false;
		    }
		    if (cliente.getData_nascimento() == null) {
		      return false;
		    }
		    return true;
	}
}
