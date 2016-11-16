package controllers;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

//import java.util.ArrayList;

//import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dominio.ForneceProduto;
import ejb.fornecimentoService;

@ManagedBean
@SessionScoped
public class FornecimentoMB {

	
	
	
	
	
	@EJB
	private fornecimentoService fornecimentoservice;
	

	
	private ForneceProduto nFornece;
	




	public ForneceProduto getnFornece() {
		return nFornece;
	}


	public void setnFornece(ForneceProduto nFornece) {
		this.nFornece = nFornece;
	}

	
	String nomeFornecedor;
	
	

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}


	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	String nomeCategoria;


	String nomeProdCarrinho;
	double quntProdCarrinho;
	
	





	public double getQuntProdCarrinho() {
		return quntProdCarrinho;
	}

	public void setQuntProdCarrinho(double quntProdCarrinho) {
		this.quntProdCarrinho = quntProdCarrinho;
	}




	
	public String getNomeProdCarrinho() {
		return nomeProdCarrinho;
	}

	public void setNomeProdCarrinho(String nomeProdCarrinho) {
		this.nomeProdCarrinho = nomeProdCarrinho;
	}


	
	
	double valorTotal;
	
	
	
	
	
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	
	
	
	public FornecimentoMB() {
		nFornece=new ForneceProduto();
		
		
	}
		
	


	public void adicionarAoCarrinho() {
		int resultado=fornecimentoservice.adicionarAoCarrinho(nomeProdCarrinho, quntProdCarrinho, tabelaFatura);
		
		if(resultado==1){
			FacesMessage msg = new FacesMessage("Coloque nome para o Produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}else if(resultado==2){
			FacesMessage msg = new FacesMessage("Coloque um valor maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;

		}else if(resultado==3){
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;

		}else if(resultado==4){


			
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			return;
			
		}
		/**
		if(nomeProdCarrinho.equals("")){
			FacesMessage msg = new FacesMessage("Coloque nome para o Produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		if(quntProdCarrinho<=0){
			
			FacesMessage msg = new FacesMessage("Coloque um valor maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		
		
		
		
		produto=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
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
			
			System.out.println(PrecoProdutos.get(j));
			double arredondarPreco=truncarValor(PrecoProdutos.get(j));
			
			
			
			addAction(nomeProdutos.get(j),arredondarPreco);
			
			
			
			
			
			

		}
		*/
	}
	
	public void remover() {
		int resultado=fornecimentoservice.remover(nomeProdCarrinho, tabelaFatura);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Coloque um Nome Para Produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}else if(resultado==1){
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
		
		}else if(resultado==2){
			FacesMessage msg = new FacesMessage("Produto não está no carrinho");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
		}
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		/**
		if(nomeProdCarrinho.equals("")){
			return;
		}
		
		
		produto=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}
		
		String tempNome=produto.getNome();
		
		
		if(nomeProdutos.contains(tempNome)){
		}else{
			FacesMessage msg = new FacesMessage("Produto não está no carrinho");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;				
		}
		
		
		int swit=-1;
	
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
				nomeProdutos.remove(swit);
				PrecoProdutos.remove(swit);	
			}
			
			
			
			
			strings.clear();
			tabelaFatura.clear();
			
			for(int j=0;j<nomeProdutos.size();j++){
				
				double arredondarPreco=truncarValor(PrecoProdutos.get(j));
				addAction(nomeProdutos.get(j),arredondarPreco);
				
				
				
			}
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			
			
			
			
			
			
			return; 
			*/
			
		}
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String finalizarFornecimento() {
		int resultado=fornecimentoservice.finalizarFornecimento(nomeFornecedor, tabelaFatura);
		
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Fornecimento sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}else if(resultado==1){
			FacesMessage msg = new FacesMessage("Digite um nome para o Fornecedor");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}else if(resultado==2){
			FacesMessage msg = new FacesMessage("Fornecedor não existe");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}else if(resultado==3){
			
		}
		
		tabelaFatura.clear();
		nomeFornecedor="";
		
		return "/interna/sucesso.jsf";

		
		
	}
	

	

	
	
	public String novo() {
		return "/interna/cadastra.jsf";
	}
	
	
	
	private static final ArrayList<ParNomePreco> tabelaFatura =
			new ArrayList<ParNomePreco>();

		public ArrayList<ParNomePreco> getTabelaFatura() {

			return tabelaFatura;

		}
	
	public static class ParNomePreco{
		public ParNomePreco() {
			super();
		}

		String nome;
		double Preco;
		

		public ParNomePreco(String nome, double preco) {
			super();
			this.nome = nome;
			Preco = preco;
			
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public double getPreco() {
			return Preco;
		}

		public void setPreco(double preco) {
			Preco = preco;
		}


		
	}
	public void limpar(){
		nFornece=new ForneceProduto();
		tabelaFatura.clear();
		fornecimentoservice.limpar();
		nomeFornecedor="";
		
	}
	
	public String voltarMenu(){
		//limpar();
		return "/interna/MenuGerente.jsf";
	}
	
	
	
}