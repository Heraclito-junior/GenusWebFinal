package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Contato;
import dominio.Funcionario;
import dominio.Usuario;

@Stateless
public class FuncionarioDAO {
	@PersistenceContext
	private EntityManager em;
	Funcionario func=new Funcionario();
	
	
	public void salvar(Funcionario c) {
		em.persist(c);
	}
	
	public void atualizar(Funcionario c) {
		em.merge(c);
	}
	
	public void remover(Funcionario c) {
		c = em.find(Funcionario.class, c.getID());
		em.remove(c);
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listar() {
		String qs = "select c from Funcionario c";
		Query q = em.createQuery(qs);
		return (List<Funcionario>) q.getResultList();
	}
	
	public Funcionario buscarFuncionarioID(String funcid) {
		
		String qs = "select c from Funcionario c where c.id = :funcid";
		Query q = em.createQuery(qs);
		q.setParameter("funcid", funcid);
		try {
			return (Funcionario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	public Funcionario buscarLogin(String id) {
		return (Funcionario) em.find(Funcionario.class, id);
	}

	public Funcionario cadastrarPrimeiro() { 
		// TODO Auto-generated method stub
		String qs = "select c from Funcionario c where c.id = :funcid";
		Query q = em.createQuery(qs);
		q.setParameter("funcid", "a");
		try {
			return (Funcionario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
