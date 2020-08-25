package com.shx.locacao.veiculos.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shx.locacao.veiculos.model.Aluguel;
import com.shx.locacao.veiculos.model.Cliente;
import com.shx.locacao.veiculos.model.Devolucao;
import com.shx.locacao.veiculos.model.Veiculo;

@Repository
public class AluguelRepository {

	@PersistenceContext
	private EntityManager em;
	private VeiculoRepository vr;
	private ClienteRepository cr;
	
	public List<Aluguel> findAll() {
		try {
			String sql = "SELECT * FROM ALUGUEL";
			Query query = em.createNativeQuery(sql, Aluguel.class);
			@SuppressWarnings("unchecked")
			List<Aluguel> alugueis = query.getResultList();

			return alugueis;
		} finally {
			em.close();
		}
	}

	public List<Aluguel> findByIdCliente(Integer id) {
		try {
			String sql = "SELECT * FROM ALUGUEL WHERE COD_CLIENTE = :ID";
			Query query = em.createNativeQuery(sql, Aluguel.class);
			query.setParameter("ID", id);
			@SuppressWarnings("unchecked")
			List<Aluguel> alugueis = query.getResultList();

			return alugueis;
		} finally {
			em.close();
		}
	}
	
	public Aluguel findById(Integer id) {
		try {
			Aluguel aluguel = em.find(Aluguel.class, id);

			return aluguel;
		} finally {
			em.close();
		}
	}
	
	public Devolucao findDevolutationByAluguel(Aluguel aluguel) {			
			return this.constructorDelovucao(aluguel); 
	}
	
	public Devolucao findDevolutationById(Integer id) {
			Aluguel al = new Aluguel();
			al = this.findById(id);

			return this.constructorDelovucao(al); 
	}
	
	public Devolucao constructorDelovucao(Aluguel al) {
		Devolucao dev = this.transformRent(al);
		Veiculo vei = new Veiculo();
		Cliente cl = new Cliente();
		vei = vr.findById(dev.getCod_veiculo());
		cl = cr.findActiveClienteById(dev.getCod_cliente());
		dev.setNome_veiculo(vei.getNome());
		dev.setNome_cliente(cl.getNome());
		
		return dev;
	}
	
	public List<Aluguel> findAluguelByIdCliente(Integer id) {
		try {
			String sql = "SELECT * FROM ALUGUEL WHERE COD_CLIENTE LIKE :codigo";
			Query query = em.createNativeQuery(sql, Aluguel.class);
			query.setParameter("codigo", id);
			@SuppressWarnings("unchecked")
			List<Aluguel> aluguel = query.getResultList();
			
			return aluguel;
		} finally {
			em.close();
		}
	}
	
	@Transactional
	public void delete(Integer id) {
			Aluguel aluguel = em.find(Aluguel.class, id);
			vr.updateState(aluguel.getCod_veiculo(), false);
			em.remove(aluguel);
	}

	@Transactional
	public Aluguel save(Aluguel aluguel) {
		try {
			if(aluguel.getCod_cliente() != null & aluguel.getCod_veiculo() != null) {
				if(aluguel.getCod_aluguel() ==  null) {
					em.persist(aluguel);
				} else {
					aluguel = em.merge(aluguel);
				}
			}
			
		} finally {
			em.close();
		}
		return aluguel;
	}
	
	private Devolucao transformRent(Aluguel aluguel) {
		Devolucao dev = new Devolucao();
		dev.setCod_aluguel(aluguel.getCod_aluguel());
		dev.setCod_cliente(aluguel.getCod_cliente());
		dev.setData_devolucao(aluguel.getData_devolucao());
		dev.setData_locacao(aluguel.getData_locacao());
		dev.setStatus(aluguel.isStatus());
		dev.setCod_veiculo(aluguel.getCod_veiculo());
		dev.setValor_total(aluguel.getValor_total());
		return dev;
	}
}
