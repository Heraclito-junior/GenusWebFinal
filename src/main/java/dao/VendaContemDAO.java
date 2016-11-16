package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Categoria;
import dominio.Contato;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Usuario;
import dominio.Venda;
import dominio.VendaContem;

@Stateless
public class VendaContemDAO {
	@PersistenceContext
	private EntityManager em;
	
	
	
	public void salvar(VendaContem c) {
		em.persist(c);
	}
	
	@SuppressWarnings("unchecked")
	public List<VendaContem> listar(int id) {
		String qs = "select c from VendaContem c where c.venda_id= :temp";
		Query q = em.createQuery(qs);
		q.setParameter("temp", id);
		return (List<VendaContem>) q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<VendaContem> buscarVendasComProdutos(Venda vendaTemp) {
		String qs = "select c from VendaContem c where c.venda_id = :temp";
		Query q = em.createQuery(qs);
		q.setParameter("temp", vendaTemp);
		try {
			return (List<VendaContem>) q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Venda buscarVendaID(int id) {
		String qs = "select c from Venda c where c.idVenda = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", id);
		try {
			return (Venda) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	
}
