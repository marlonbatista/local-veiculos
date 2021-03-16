package com.shx.locacao.veiculos.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@RequestMapping(value = "/api/v1/aluguel", produces = MediaType.APPLICATION_JSON_VALUE)
public class AluguelController {

	@Autowired
	private AluguelRepository repositorio;
	@Autowired
	private ClienteRepository resCliente;
	@Autowired
	private VeiculoRepository resVeiculo;

	@GetMapping(path = "/")
	public List<Aluguel> buscarAlugueis() throws Exception {
		List<Aluguel> alugueis = repositorio.findAll();
		return alugueis;
	}

	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	public Aluguel findById(@PathVariable("id") Integer id) throws Exception {
		if (id == null) {
			throw new Exception("Id inválido");
		}
		Aluguel alug = new Aluguel();
		alug =repositorio.findById(id);
		alug.setValor_total(this.calculateTotalValue(alug));
		return alug;		
	}

	@RequestMapping(path = "/devolucao/{id}", method = { RequestMethod.GET })
	public Devolucao findByIdDevolucao(@PathVariable("id") Integer id) throws Exception {
		if (id == null) {
			throw new Exception("Id inválido");
		}
		Aluguel aluguel = new Aluguel();
		aluguel =  repositorio.findById(id);
		Devolucao dev = new Devolucao();
		dev = preencherDevolucao(aluguel);
		return dev;
	}

	@RequestMapping(path = "/cliente/{id}", method = { RequestMethod.GET })
	public List<AluguelCliente> findByIdCliente(@PathVariable("id") Integer id) throws Exception {
		if (id == null) {
			throw new Exception("Id inválido");
		}
		List<Aluguel> alugueis = repositorio.findAluguelByIdCliente(id);
		List<AluguelCliente> list = this.addCliente(alugueis, id);
		return list;
	}

	@PostMapping(path = "/")
	public Aluguel create(@RequestBody Aluguel aluguel) throws Exception {
		if (this.validationClienteVeiculo(aluguel) != null) {
			String message = this.validationClienteVeiculo(aluguel);
			throw new Exception(message);
		}
		
		if (aluguel.getCod_aluguel() == null) {
			
			if (this.isValidToCreate(aluguel) == false) {
				throw new Exception("dados inválidos");
			}
			resVeiculo.updateState(aluguel.getCod_veiculo(), true);
		} else {
			aluguel.setValor_total(null);
			if (this.isValid(aluguel) == false) {
				throw new Exception("dados inválidos");
			}
			float total = this.calculateTotalValue(aluguel);
			aluguel.setValor_total(total);
			resVeiculo.updateState(aluguel.getCod_veiculo(), false);
		}
		return repositorio.save(aluguel);
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public void delete(@PathVariable("id") Integer id) throws Exception {
		if (id == null) {
			throw new Exception("Id inválido");
		}
		repositorio.delete(id);
	}

	private boolean isValidToCreate(Aluguel aluguel) {
		if (aluguel.getCod_cliente() == null || aluguel.getCod_veiculo() == null) {
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
		if (aluguel.getCod_aluguel() == null) {
			return false;
		}
		Calendar dataD = aluguel.getData_devolucao();
		Calendar dataL = aluguel.getData_locacao();
		if (dataD == null || dataD.getTimeInMillis() < dataL.getTimeInMillis()) {
			return false;
		}
		return this.isValidToCreate(aluguel);
	}

	private String validationClienteVeiculo(Aluguel aluguel) {
		if (this.idClienteIsValid(aluguel.getCod_cliente()) == false) {
			return "Cliente inválido na base de dados";
		}
		if (this.idVeiculoIsValid(aluguel.getCod_veiculo()) == false) {
			return "Veículo inválido na base de dados";
		}
		return null;
	}

	private boolean idClienteIsValid(Integer id) {
		Cliente cliente = resCliente.findActiveClienteById(id);
		if (cliente.getCod_cliente() == null) {
			return false;
		}
		return true;
	}

	private boolean idVeiculoIsValid(Integer id) {
		Veiculo veiculo = new Veiculo();
		veiculo = resVeiculo.findById(id);
		if (veiculo.getCod_veiculo() == null) {
			return false;
		}
		return true;
	}

	private float calculateTotalValue(Aluguel aluguel) {
		Float valor_diaria = resVeiculo.findById(aluguel.getCod_veiculo()).getValor_diaria();
		Calendar cal = new GregorianCalendar();

		if(aluguel.getData_devolucao() == null) {
			Date dt = new Date();
			cal.setTime(dt);
		} else
			cal.setTime(aluguel.getData_devolucao().getTime());
			
		Long dias = this.subtrairData(aluguel.getData_locacao(), cal);
		float total;
		if(dias == 0)
			total = valor_diaria;
		else
			total = valor_diaria * dias;
		return total;
	}

	private long subtrairData(Calendar dateL, Calendar dateD) {
		long end = dateD.getTimeInMillis();
		long start = dateL.getTimeInMillis();
		return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	}

	private List<AluguelCliente> addCliente(List<Aluguel> items, Integer id) {
		String nome = resCliente.findById(id).getNome();
		List<AluguelCliente> list = new ArrayList<AluguelCliente>();
		for (Aluguel item : items) {
			AluguelCliente al = new AluguelCliente();
			al.setCod_aluguel(item.getCod_aluguel());
			al.setCod_cliente(item.getCod_cliente());
			al.setCod_veiculo(item.getCod_veiculo());
			al.setData_devolucao(item.getData_devolucao());
			al.setStatus(item.isStatus());
			al.setValor_total(item.getValor_total());
			al.setData_locacao(item.getData_locacao());

			al.setNome_cliente(nome);
			list.add(al);
		}
		return list;
	}
	
	private Cliente retornarClienteAluguel(Integer id) {
		return resCliente.findById(id);
	}
	
	private Veiculo retornarVeiculoAluguel(Integer id) {
		return resVeiculo.findById(id);
	}
	
	public Devolucao preencherDevolucao(Aluguel aluguel) {
		Devolucao dev = new Devolucao();
		
		Cliente cli = new Cliente();
		cli = this.retornarClienteAluguel(aluguel.getCod_cliente());
		
		Veiculo vei = new Veiculo();
		vei = this.retornarVeiculoAluguel(aluguel.getCod_veiculo());
		
		dev.setCod_aluguel(aluguel.getCod_aluguel());
		dev.setData_devolucao(aluguel.getData_devolucao());
		dev.setData_locacao(aluguel.getData_locacao());
		dev.setStatus(aluguel.isStatus());
		dev.setCod_veiculo(aluguel.getCod_veiculo());
		dev.setValor_total(aluguel.getValor_total());
		dev.setCod_cliente(cli.getCod_cliente());
		dev.setCod_veiculo(vei.getCod_veiculo());
		dev.setNome_cliente(cli.getNome());
		dev.setNome_veiculo(vei.getNome());
		dev.setValor_total(this.calculateTotalValue(aluguel));
		
		return dev;
	}
}
