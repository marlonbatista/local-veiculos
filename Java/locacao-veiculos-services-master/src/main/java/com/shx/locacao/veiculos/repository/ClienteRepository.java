package com.shx.locacao.veiculos.repository;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.shx.locacao.veiculos.model.Cliente;

@Repository
public class ClienteRepository {
	
	@PersistenceContext
	   private EntityManager em;
	
	public List<Cliente> findAll() {
		try {
			String sql = "SELECT * FROM CLIENTE";
			Query query = em.createNativeQuery(sql, Cliente.class);
			@SuppressWarnings("unchecked")
			List<Cliente> clientes = query.getResultList();
			
			return clientes;
		} finally {
			em.close();
		}
	}
	
	public Cliente findById(Integer id) {
		try {
		Cliente cliente = em.find(Cliente.class, id);
		
		return cliente;
		} finally {
			em.close();
		}
	}
	
	public void delete(Integer id) {
		try {
			Cliente cliente = findById(id);
			em.remove(cliente);
		} finally {
			em.clear();
		}
	}
	
	public void save(Cliente cliente) throws Exception {
		try {
			if(cliente.getCod_cliente() ==  null) {
				em.persist(cliente);
			} else {
				cliente = em.merge(cliente);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
}
