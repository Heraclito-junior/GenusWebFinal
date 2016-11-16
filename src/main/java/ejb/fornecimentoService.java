package ejb;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import controllers.FornecimentoMB.ParNomePreco;
import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Produto;

@Stateful
public class fornecimentoService {
	
	public List<String> getNomeProdutos() {
		return nomeProdutos;
	}

	public void setNomeProdutos(List<String> nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}

	public List<Double> getQuantidadeProdutos() {
		return quantidadeProdutos;
	}

	public void setQuantidadeProdutos(List<Double> quantidadeProdutos) {
		this.quantidadeProdutos = quantidadeProdutos;
	}

	public List<Double> getPrecoProdutos() {
		return PrecoProdutos;
	}

	public void setPrecoProdutos(List<Double> precoProdutos) {
		PrecoProdutos = precoProdutos;
	}

	public ForneceProduto getnFornece() {
		return nFornece;
	}

	public void setnFornece(ForneceProduto nFornece) {
		this.nFornece = nFornece;
	}

	List<String> nomeProdutos;
	List<Double> quantidadeProdutos;
	List<Double> PrecoProdutos;
	
	ForneceProduto nFornece;
	
	
	@Inject 
	FornecedorDAO fornecedorDAO;
	
	@Inject 
	ProdutoDAO produtoDAO;
	
	@Inject
	private ForneceProdutoDAO forneceProdutoDAO;

	public fornecimentoService() {
		super();
		nFornece=new ForneceProduto();
		nomeProdutos=new ArrayList<String>();
		quantidadeProdutos=new ArrayList<Double>();
		PrecoProdutos=new ArrayList<Double>();
	
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int adicionarAoCarrinho(String nomeProdCarrinho, double quntProdCarrinho,ArrayList<ParNomePreco> tabelaFatura){
		System.out.println("ejb 123");
		if(nomeProdCarrinho.equals("")){
			
			return 1;
		}
		if(quntProdCarrinho<=0){
			
			
			return 2;
		}
		
		
		
		Produto produtoTemp=new Produto();
		produtoTemp=produtoDAO.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produtoTemp==null){
			
			
			return 3;
			
		}
		
		String tempNome=nomeProdCarrinho;
		int swit=-1;
		
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
		}
		
		
		
		
		
		
		if(swit==-1){
		nomeProdutos.add(nomeProdCarrinho);
		
		PrecoProdutos.add(quntProdCarrinho);
		}else{
			
			PrecoProdutos.set(swit, quntProdCarrinho);
			
			
			
			
		}
		
		tabelaFatura.clear();
		
		for(int j=0;j<nomeProdutos.size();j++){
			
			double arredondarPreco=truncarValor(PrecoProdutos.get(j));
			
			addAction(nomeProdutos.get(j),arredondarPreco,tabelaFatura);
			
		}
		

		
		
		return 4; 
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int remover(String nomeProdCarrinho, ArrayList<ParNomePreco> tabelaFatura) {
		if(nomeProdCarrinho.equals("")){
			return 0;
		}
		
		
		Produto produto=new Produto();
		produto=produtoDAO.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			
			return 1;
			
		}
		
		String tempNome=produto.getNome();
		
		
		if(nomeProdutos.contains(tempNome)){
		}else{
			
			return 2;				
		}
		
		
		int swit=-1;
	
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
				nomeProdutos.remove(swit);
				PrecoProdutos.remove(swit);	
			}
			
			
			
			
			tabelaFatura.clear();
			
			for(int j=0;j<nomeProdutos.size();j++){
				
				double arredondarPreco=truncarValor(PrecoProdutos.get(j));
				addAction(nomeProdutos.get(j),arredondarPreco,tabelaFatura);
				
				
				
			}
			

			
			return 3; 
			
		}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int finalizarFornecimento(String fornecedor,ArrayList<ParNomePreco> tabelaFatura) {
		
		if(nomeProdutos.size()==0){
			
			return 0;
		}
		
		
		
		Fornecedor fornecedorTemp=new Fornecedor();
		fornecedorTemp=fornecedorDAO.buscarFornecedorNome(fornecedor);
		
		if(fornecedorDAO.buscarFornecedorNome(fornecedor)==null){
			
			if(fornecedor==""){
				
				return 1;
			}else{
				
				return 2;
			}
		}
		
		
		fornecedorTemp=fornecedorDAO.buscarFornecedorNome(fornecedor);
		
		for(int i=0;i<nomeProdutos.size();i++){
			
			nFornece.setFid(fornecedorTemp);
			Produto temp=new Produto();
			temp=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			nFornece.setProdutoFornecimento(temp);
			double arredondar=truncarValor(PrecoProdutos.get(i));
			nFornece.setPrecoNoMomento(arredondar);
			
			ForneceProduto u=forneceProdutoDAO.existeComb(fornecedorTemp, temp);
			
			if(u==null){
				forneceProdutoDAO.salvar(nFornece);
				
			}else{
				
				forneceProdutoDAO.remover(u);
				forneceProdutoDAO.salvar(nFornece);
			}
			nFornece=new ForneceProduto();
			
			
		
		}
		
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		nFornece=new ForneceProduto();
		tabelaFatura.clear();
		return 3;
		
			
		}
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String addAction(String nome, double preco,ArrayList<ParNomePreco> tabelaFatura) {
	

		
		Produto order=produtoDAO.buscarProdutoNome(nome);
		if(order==null){
			FacesMessage msg = new FacesMessage("Não existe produto com esse nome");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
							
			return null;
		}
		
		ParNomePreco auxiliarDataTable=new ParNomePreco(nome,preco);
		
		
		

		tabelaFatura.add(auxiliarDataTable);
		return null;
	}
	
	public double truncarValor(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public double truncarQuantidade(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.###");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public void limpar(){
		nomeProdutos.clear();
		PrecoProdutos.clear();
		
	}
	

	

}