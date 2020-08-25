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

import com.shx.locacao.veiculos.model.Veiculo;
import com.shx.locacao.veiculos.repository.VeiculoRepository;

@CrossOrigin
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
	
	@RequestMapping(path = "/{id}", method= {RequestMethod.GET})
	public Veiculo findById(@PathVariable("id") Integer id) throws Exception{
		if(id == null) {
			throw new Exception("Id inválido");
		} 
		return repositorio.findById(id);
	}
	
	@PostMapping(path= "/")
	public Veiculo create(@RequestBody Veiculo veiculo) throws Exception{
		if(this.isValid(veiculo) == false) {
			throw new Exception("dados inválidos");
		}
	   return repositorio.save(veiculo);
	}
		
	 @RequestMapping(value="/{id}", method= {RequestMethod.DELETE})
	 public void delete( @PathVariable("id") Integer id) throws Exception {
		 if(id == null) {
			 throw new Exception("id inválido");
		 }
		 repositorio.delete(id);
	 }
	 
	private boolean isValid(Veiculo veiculo) {
		if (veiculo.getAno() < 2000 || veiculo.getAno() == null) {
		      return false;
		    }
		    if (veiculo.getModelo() < veiculo.getAno()) {
		      return false;
		    }
		    if (veiculo.getMarca().isBlank()||
		    		veiculo.getMarca().isEmpty() ||
		    		veiculo.getMarca().length() > 30) {
		      return false;
		    }
		    if (veiculo.getNome().isBlank() ||
		    		veiculo.getNome().isEmpty() ||
		    		veiculo.getNome().length() > 100
		    		) {
		      return false;
		    }
		    if (veiculo.getValor_diaria() < 0.1	) {
		      return false;
		    }
		    if (veiculo.getCombustivel() == null) {
		    	return false;
		    }
		    
		    return true;
	}
}
