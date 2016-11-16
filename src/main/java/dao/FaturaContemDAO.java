package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Contato;
import dominio.Fatura;
import dominio.FaturaReferenteProduto;
import dominio.Funcionario;
import dominio.Venda;
import dominio.VendaContem;

@Stateless
public class FaturaContemDAO {
	@PersistenceContext
	private EntityManager em;
	
	
	
	public void salvar(FaturaReferenteProduto c) {
		em.persist(c);
	}
	
	public Fatura buscarFaturaID(int id) {
		String qs = "select c from Fatura c where c.IDFat = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", id);
		try {
			return (Fatura) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FaturaReferenteProduto> buscarFaturasComProdutos(Fatura FaturaTemp) {
		String qs = "select c from FaturaReferenteProduto c where c.faturaReferenteid = :temp";
		Query q = em.createQuery(qs);
		q.setParameter("temp", FaturaTemp);
		try {
			return (List<FaturaReferenteProduto>) q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}