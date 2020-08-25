package com.shx.locacao.veiculos.repository;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	public Cliente findActiveClienteById(Integer id) {
		try {
			String sql = "SELECT * FROM CLIENTE WHERE COD_CLIENTE = :ID AND STATUS = TRUE";
			Query query = em.createNativeQuery(sql, Cliente.class);
			query.setParameter("ID", id);
			List<Cliente> cli = new ArrayList<Cliente>();
			cli = query.getResultList();
			
			return cli.get(0);
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

	@Transactional
	public void delete(Integer id) {
			Cliente cliente = em.find(Cliente.class, id);
			em.remove(cliente);
	}

	@Transactional
	public Cliente save(Cliente cliente) {
		try {
			if(cliente.getCod_cliente() ==  null) {
				em.persist(cliente);
			} else {
				cliente = em.merge(cliente);
			}
		} finally {
			em.close();
		}
		return cliente;
	}

}
