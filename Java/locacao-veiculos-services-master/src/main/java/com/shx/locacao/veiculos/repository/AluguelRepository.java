package com.shx.locacao.veiculos.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shx.locacao.veiculos.model.Aluguel;

@Repository
public class AluguelRepository {

	@PersistenceContext
	private EntityManager em;
	private VeiculoRepository vr;
	
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
	
}
