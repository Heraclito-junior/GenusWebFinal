package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Categoria;
import dominio.Contato;

@Stateless
public class CategoriaDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void salvar(Categoria c) {
		em.persist(c);
	}
	
	public void atualizar(Categoria c) {
		em.merge(c);
	}
	
	public void remover(Categoria c) {
		c = em.find(Categoria.class, c.getIDcategoria());
		em.remove(c);
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> listar() {
		String qs = "select c from Categoria c";
		Query q = em.createQuery(qs);
		return (List<Categoria>) q.getResultList();
	}
	
	public Categoria buscarCategoriaNome(String nome) {
		String qs = "select c from Categoria c where c.nome = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", nome);
		try {
			return (Categoria) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
