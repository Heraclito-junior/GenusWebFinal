package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Contato;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;
import dominio.VendaContem;


@Stateless
public class ForneceProdutoDAO {
	@PersistenceContext
	private EntityManager em;
	
	
	
	public void salvar(ForneceProduto c) {
		em.persist(c);
	}
	public void atualizar(ForneceProduto c) {
		em.merge(c);
	}
	public void remover(ForneceProduto c) {
		
		c = em.find(ForneceProduto.class, c.getIdForneceProdutoProprio());
		em.remove(c);
		
	}
	
	public ForneceProduto existeComb(Fornecedor nomeF,Produto prod) {
		
		
		String qs = "select c from ForneceProduto c where c.fid = ?1 and c.produtoFornecimento= ?2";
		Query q = em.createQuery(qs);
		q.setParameter(1, nomeF).setParameter(2,prod);
		try {
			return (ForneceProduto) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
		
		
	}
	
	public Fornecedor buscarFornecedorpeloNome(String idFornecimento) {
		
		String qs = "select c from Fornecedor c where c.Fname = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", idFornecimento);
		try {
			return (Fornecedor) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	
	
	
	public ForneceProduto buscarFornecedorNome(int idFornecimento) {
		
		String qs = "select c from ForneceProduto c where c.fid = :nome";
		Query q = em.createQuery(qs);
		q.setParameter("nome", idFornecimento);
		try {
			return (ForneceProduto) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ForneceProduto> buscarFornecimentoComFornecedor(Fornecedor fTemp) {
		String qs = "select c from ForneceProduto c where c.fid = :temp";
		Query q = em.createQuery(qs);
		q.setParameter("temp", fTemp);
		try {
			return (List<ForneceProduto>) q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
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
	
	
	
	
	
	
}