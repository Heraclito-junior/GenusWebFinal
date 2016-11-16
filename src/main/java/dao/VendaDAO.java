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
import dominio.Venda;

@Stateless
public class VendaDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void salvar(Venda c) {
		em.persist(c);
	}
	
	public void atualizar(Venda c) {
		em.merge(c);
	}
	
	public void remover(Venda c) {
		c = em.find(Venda.class, c.getIdVenda());
		em.remove(c);
	}
	
	
	public Produto buscarProdutoNome(String nome) {
		String qs = "select c from Produto c where c.Nome = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", nome);
		try {
			return (Produto) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Venda> listar() {
		String qs = "select c from Venda c";
		Query q = em.createQuery(qs);
		return (List<Venda>) q.getResultList();
	}


}
