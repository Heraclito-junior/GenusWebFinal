package ejb;

import java.util.List;


import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dominio.ForneceProduto;
import dominio.Fornecedor;

@Stateless
public class fornecedorService {

	@Inject
	private ForneceProdutoDAO forneceContemDao;
	
	
	@Inject 
	FornecedorDAO fornecedorDAO;

	public fornecedorService() {
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
	
	public int filtrarFornecedor(String idFornecimento, List<ForneceProduto> listaFaturaContem){
		//listaFaturaContem.g
		Fornecedor fornecedorTemporario=forneceContemDao.buscarFornecedorpeloNome(idFornecimento);
		if(fornecedorTemporario==null){
			
			return 0;
		}
		return 1;
	}
	
	public List<ForneceProduto> detalhar(String idFornecedor){
		List<ForneceProduto> listaFaturaContem2;
		Fornecedor temp=fornecedorDAO.buscarFornecedorNome(idFornecedor);
		listaFaturaContem2=forneceContemDao.buscarFornecimentoComFornecedor(temp);
		
		return  listaFaturaContem2;
		
	}
	

	
	
	
	
	
	
	
	
	
}