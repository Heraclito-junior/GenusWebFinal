package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Categoria;
import dominio.Cliente;
import dominio.Contato;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;

@Stateless
public class ClienteDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void salvar(Cliente c) {
		em.persist(c);
	}
	
	public void atualizar(Cliente c) {
		em.merge(c);
	}
	public void remover(Contato c) {
		c = em.find(Contato.class, c.getId());
		em.remove(c);
	}
	
	public void remover(Cliente c) {
		c = em.find(Cliente.class, c.getCPF());
		em.remove(c);
	}
	
	
	public Cliente buscarClienteCPF(long cpf2) {
		String qs = "select c from Cliente c where c.CPF = :temp";
		Query q = em.createQuery(qs);
		q.setParameter("temp", cpf2);
		try {
			return (Cliente) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listar() {
		String qs = "select c from Cliente c";
		Query q = em.createQuery(qs);
		return (List<Cliente>) q.getResultList();
	}
	



}
