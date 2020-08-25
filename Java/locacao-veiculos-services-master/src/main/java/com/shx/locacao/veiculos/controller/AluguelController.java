package com.shx.locacao.veiculos.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

import com.shx.locacao.veiculos.model.Aluguel;
import com.shx.locacao.veiculos.model.AluguelCliente;
import com.shx.locacao.veiculos.model.Cliente;
import com.shx.locacao.veiculos.model.Devolucao;
import com.shx.locacao.veiculos.model.Veiculo;
import com.shx.locacao.veiculos.repository.AluguelRepository;
import com.shx.locacao.veiculos.repository.ClienteRepository;
import com.shx.locacao.veiculos.repository.VeiculoRepository;

@CrossOrigin
@RestController 
@RequestMapping(value="/api/v1/aluguel",produces = MediaType.APPLICATION_JSON_VALUE )
public class AluguelController {
	
	@Autowired
	private AluguelRepository repositorio;
	@Autowired
	private ClienteRepository resCliente;
	@Autowired
	private VeiculoRepository resVeiculo;
	
	
	@GetMapping(path = "/")
	public List<Aluguel> buscarAlugueis () throws Exception {
			List<Aluguel> alugueis = repositorio.findAll();
			return alugueis;
	}
	
	@RequestMapping(path = "/{id}", method= {RequestMethod.GET})
	public Aluguel findById(@PathVariable("id") Integer id) throws Exception{
		if(id == null) {
			throw new Exception("Id inválido");
		} 
		return repositorio.findById(id);
	}
	
	@RequestMapping(path = "/devolucao/{id}", method= {RequestMethod.GET})
	public Devolucao findByIdDevolucao(@PathVariable("id") Integer id) throws Exception{
		if(id == null) {
			throw new Exception("Id inválido");
		} 
		return repositorio.findDevolutationById(id);
	}
	
	
	@PostMapping(path = "/devolucao/")
	public Devolucao findDevolutionById(@RequestBody Aluguel aluguel) throws Exception{
		if(aluguel.getCod_aluguel() == null) {
			throw new Exception("Id inválido");
		} 
		return repositorio.findDevolutationByAluguel(aluguel);
	}
	
	@RequestMapping(path = "/cliente/{id}", method= {RequestMethod.GET})
	public List<AluguelCliente> findByIdCliente(@PathVariable("id") Integer id) throws Exception{
		if(id == null) {
			throw new Exception("Id inválido");
		} 
		List<Aluguel> alugueis =  repositorio.findAluguelByIdCliente(id);
		List<AluguelCliente> list = this.addCliente(alugueis, id);
		return list;
	}
	
	
	@PostMapping(path= "/")
	public Aluguel create(@RequestBody Aluguel aluguel) throws Exception{
		if(this.validationClienteVeiculo(aluguel) != null) {
			String message = this.validationClienteVeiculo(aluguel);
			throw new Exception(message);
		}
		
		if(aluguel.getCod_aluguel() == null) {
			if(this.isValidToCreate(aluguel) == false) {
				throw new Exception("dados inválidos");
			}
			resVeiculo.updateState(aluguel.getCod_veiculo(), true);
		} else {
			if(this.isValid(aluguel) == false) {
				throw new Exception("dados inválidos");	
			}
			float total = this.calculateTotalValue(aluguel);
			aluguel.setValor_total(total);			
			resVeiculo.updateState(aluguel.getCod_veiculo(), false);
		}
		return repositorio.save(aluguel);
	}
	
	
	@RequestMapping(value="/{id}", method= {RequestMethod.DELETE})
	 public void delete( @PathVariable("id") Integer id) throws Exception {
		if(id == null) {
			throw new Exception("Id inválido");	
		}
		repositorio.delete(id);
	 }
	
	private boolean isValidToCreate(Aluguel aluguel) {
		if (aluguel.getCod_cliente() == null || 
				aluguel.getCod_veiculo() == null) {
		      return false;
		}
		if (aluguel.getValor_total() != null) {
			return false;
		}
		if (aluguel.getData_locacao() == null) {
		      return false;
		}
		return true;
	}
	
	private boolean isValid(Aluguel aluguel) {
		if(aluguel.getCod_aluguel() == null) {
			return false;
		}
		Calendar dataD = aluguel.getData_devolucao();
		Calendar dataL = aluguel.getData_locacao();
		if(dataD == null || dataD.getTimeInMillis() <  dataL.getTimeInMillis()) {
			return false;
		}
		return this.isValidToCreate(aluguel);
	}
	
	private String validationClienteVeiculo(Aluguel aluguel) {
		if(this.idClienteIsValid(aluguel.getCod_cliente()) == false) {
			return "Cliente inválido na base de dados";
		}
		if(this.idVeiculoIsValid(aluguel.getCod_veiculo()) == false ) {
			return "Veículo inválido na base de dados";
		}
		return null;
	}
	
	private boolean idClienteIsValid(Integer id) {
		Cliente cliente  = resCliente.findActiveClienteById(id);
		if(cliente.getCod_cliente() == null) {
			return false;
		}
		return true;
	}
	
	private boolean idVeiculoIsValid(Integer id) {
		Veiculo veiculo = new Veiculo();
		veiculo = resVeiculo.findById(id);
		if(veiculo.getCod_veiculo() == null) {
			return false;
		}
		return true;
	}
	
	private float calculateTotalValue(Aluguel aluguel) {
		Float valor_diaria = resVeiculo.findById(aluguel.getCod_veiculo()).getValor_diaria();
		Long days = this.subtractDate(aluguel.getData_locacao(), aluguel.getData_devolucao());
		float total = valor_diaria * days;
		return total;
	}
	
	private long subtractDate(Calendar dateL, Calendar dateD) {
		long end = dateD.getTimeInMillis();
	    long start = dateL.getTimeInMillis();
	    return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	}
	
	private List<AluguelCliente> addCliente(List<Aluguel> items, Integer id){
		String nome = resCliente.findById(id).getNome();
		List<AluguelCliente> list =  new ArrayList<AluguelCliente>();
		for(Aluguel item : items) {
			AluguelCliente al = new AluguelCliente();
			al.setCod_aluguel(item.getCod_aluguel());
			al.setCod_cliente(item.getCod_cliente());
			al.setCod_veiculo(item.getCod_veiculo());
			al.setData_devolucao(item.getData_devolucao());
			al.setStatus(item.isStatus());
			al.setValor_total(item.getValor_total());
			
			al.setNome_cliente(nome);
			list.add(al);
		}
		return list;
	}
}
