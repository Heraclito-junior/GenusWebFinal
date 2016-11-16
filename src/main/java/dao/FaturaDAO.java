package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Categoria;
import dominio.Contato;
import dominio.Fatura;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;

@Stateless
public class FaturaDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void salvar(Fatura c) {
		em.persist(c);
	}
	
	public void atualizar(Fatura c) {
		em.merge(c);
	}
	
	public void remover(Fatura c) {
		c = em.find(Fatura.class, c.getIDFat());
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
	public List<Fatura> listar() {
		String qs = "select c from Fatura c";
		Query q = em.createQuery(qs);
		return (List<Fatura>) q.getResultList();
	}
	

	
	


}
