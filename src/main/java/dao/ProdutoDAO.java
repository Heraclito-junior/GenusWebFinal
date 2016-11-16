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

@Stateless
public class ProdutoDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void salvar(Produto c) {
		em.persist(c);
	}
	
	public void atualizar(Produto c) {
		em.merge(c);
	}
	
	public void remover(Produto c) {
		c = em.find(Produto.class, c.getIdProduto());
		em.remove(c);
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> listar() {
		String qs = "select c from Produto c";
		Query q = em.createQuery(qs);
		return (List<Produto>) q.getResultList();
	}
	
	public Categoria retornaCategoria(String nomeDaCategoriaUsada){
		String qs = "select c from Categoria c where c.nome = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", nomeDaCategoriaUsada);
		try {
			return (Categoria) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
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
	}
	
	
	
	
}
