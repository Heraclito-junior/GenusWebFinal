package ejb;

import java.util.List;


import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dao.VendaContemDAO;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Venda;

@Stateless
public class vendidoService {

	@Inject
	private ForneceProdutoDAO forneceContemDao;
	
	
	@Inject 
	FornecedorDAO fornecedorDAO;
	@Inject 
	VendaContemDAO vendaContemDAO;

	public vendidoService() {
		super();
	
	}
	public List<Fornecedor> listar(){
		return fornecedorDAO.listar();
	}
	
	public int cadastrar(String nome){
		
		Fornecedor c= fornecedorDAO.buscarFornecedorNome(nome);
		
		
		if (c == null) {
			Fornecedor temp=new Fornecedor();
			
			temp.setFname(nome);
			
			fornecedorDAO.salvar(temp);
			
			return 1;
		} else {
			
			return 2;
		}
		
	}
	
	
	

	
	
	
	
	
	
	
	
	
}