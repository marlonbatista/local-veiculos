package com.shx.locacao.veiculos.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shx.locacao.veiculos.model.Veiculo;

@Repository
public class VeiculoRepository {
	
	@PersistenceContext
	   private EntityManager em;
	
	public List<Veiculo> findAll() {
		try {
			String sql = "SELECT * FROM VEICULO";
			Query query = em.createNativeQuery(sql, Veiculo.class);
			@SuppressWarnings("unchecked")
			List<Veiculo> veiculo = query.getResultList();
			
			return veiculo;
		} finally {
			em.close();
		}
	}
	
	public Veiculo findById(Integer id) {
		try {
			Veiculo veiculo = em.find(Veiculo.class, id);
		
		return veiculo;
		} finally {
			em.close();
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		Veiculo veiculo = em.find(Veiculo.class, id);
		em.remove(veiculo);
	}
	
	@Transactional
	public Veiculo save(Veiculo veiculo) {
		try {
			if(veiculo.getCod_veiculo() ==  null) {
				em.persist(veiculo);
			} else {
				veiculo = em.merge(veiculo);
			}
		} finally {
			em.close();
		}
		return veiculo;
	}
	
	@Transactional
	public void updateState(Integer id, boolean state) {
		try {
			String sql = "UPDATE VEICULO SET STATUS = :ST WHERE COD_VEICULO = :ID";
			Query query = em.createNativeQuery(sql, Veiculo.class);
			query.setParameter("ST", state);
			query.setParameter("ID", id);
			query.executeUpdate();
			
		} finally {
			em.close();
		}
	}
	
}
