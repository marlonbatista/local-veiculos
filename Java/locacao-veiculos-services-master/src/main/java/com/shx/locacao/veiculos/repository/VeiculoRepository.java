package com.shx.locacao.veiculos.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.shx.locacao.veiculos.model.Cliente;
import com.shx.locacao.veiculos.model.Veiculo;

@Repository
public class VeiculoRepository {
	
	@PersistenceContext
	   private EntityManager em;
	
	public List<Veiculo> findAll() {
		try {
			String sql = "SELECT * FROM VEICULO";
			Query query = em.createNativeQuery(sql, Cliente.class);
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
	
	public void delete(Integer id) {
		try {
			Veiculo veiculo = findById(id);
			em.remove(veiculo);
		} finally {
			em.clear();
		}
	}
	
	public void save(Veiculo veiculo) throws Exception {
		try {
			if(veiculo.getCod_veiculo() ==  null) {
				em.persist(veiculo);
			} else {
				veiculo = em.merge(veiculo);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}
