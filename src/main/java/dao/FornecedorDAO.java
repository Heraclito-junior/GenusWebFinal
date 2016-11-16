package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Categoria;


import dominio.Fornecedor;


@Stateless
public class FornecedorDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void salvar(Fornecedor c) {
		em.persist(c);
	}
	
	public void atualizar(Fornecedor c) {
		em.merge(c);
	}
	
	public void remover(Fornecedor c) {
		c = em.find(Fornecedor.class, c.getFID());
		em.remove(c);
	}
	
	
	public Fornecedor buscarFornecedorNome(String Nome) {
		String qs = "select c from Fornecedor c where c.Fname = :temp";
		Query q = em.createQuery(qs);
		q.setParameter("temp", Nome);
		try {
			return (Fornecedor) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Fornecedor> listar() {
		String qs = "select c from Fornecedor c";
		Query q = em.createQuery(qs);
		return (List<Fornecedor>) q.getResultList();
	}


}
