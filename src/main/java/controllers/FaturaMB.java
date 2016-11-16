package controllers;

import java.math.BigDecimal;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;



import dominio.Fatura;
import dominio.Fornecedor;

import ejb.faturaService;

@ManagedBean
@ViewScoped
public class FaturaMB {
	
	@ManagedProperty(value="#{dados}")
	private DadosSistema ctrluser;
	
	public DadosSistema getCtrluser() {
		return ctrluser;
	}

	public void setCtrluser(DadosSistema ctrluser) {
		this.ctrluser = ctrluser;
	}
	

	@EJB
	private faturaService faturaservice;
	
	private List<Fatura> listaFatura;
	
	public List<Fatura> getListaFatura() {
		setListaFatura(faturaservice.listar());
		return listaFatura;
	}

	
	public void setListaFatura(List<Fatura> listaFatura) {
		this.listaFatura = listaFatura;
	}
	
	
	





	


	Fornecedor fornecedor;

	Double total;
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

	public FaturaMB() {
		
		ctrluser=new DadosSistema();
		total=0.0;
		fornecedor=new Fornecedor();
		
	}
		
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void adicionarAoCarrinho() {
		
		
		
		
		//fornecedor=fonecedorDAO.buscarFornecedorNome(ctrluser.getIdFornecedor());
		
		int resultado=faturaservice.adicionarAoCarrinho(ctrluser.getIdFornecedor(), nomeProdCarrinho, quntProdCarrinho, tabelaFatura);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Coloque um nome para o produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			
			
		}
		if(resultado==1){
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			return;
			
		}
		if(resultado==2){
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0;
			return;
			
		}
		if(resultado==3){
			FacesMessage msg = new FacesMessage("Fornecedor não fornece esse produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			return;

		}
		if(resultado==4){
			FacesMessage msg = new FacesMessage("Produto não Fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;

			
		}
		if(resultado==5){
			FacesMessage msg = new FacesMessage("Não existe produto com esse nome");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			return;

			
		}
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		total=faturaservice.getTotal();		
		return; 
	}
	
	public void remover() {
		
		int resultado=faturaservice.remover( nomeProdCarrinho, quntProdCarrinho, tabelaFatura);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Coloque um nome para o Produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			return;
		}
		if(resultado==1){
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;

		}
		if(resultado==2){
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			return;

			
		}
		if(resultado==3){
			FacesMessage msg = new FacesMessage("Produto não está no carrinho");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			quntProdCarrinho=0.0;
			return;

			
		}
		if(resultado==4){
			FacesMessage msg = new FacesMessage("Produto não Fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;

		}
		if(resultado==5){
			FacesMessage msg = new FacesMessage("Não há tanto produto no carrinho para remover");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;
			
		}
		
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		total=faturaservice.getTotal();
		
		
	}

	
	public String finalizarAsCompras() {
		//fornecedor=ctrlFornece.recuperarFornecedor();
		int resultado=faturaservice.finalizarAsCompras(ctrluser.getIdFornecedor(),nomeProdCarrinho, quntProdCarrinho, tabelaFatura,ctrluser.getIdFuncionario());
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Venda sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		
		
		
		
		
		
		total=0.0;
		
		tabelaFatura.clear();

		
		return "/interna/sucesso.jsf";
	}
	
	
	public String novo() {
		return "/interna/cadastra.jsf";
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public double truncarQuantidade(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.###");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public double truncarValor(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	} 
	
	private static final ArrayList<ProdutoModificado> tabelaFatura =
			new ArrayList<ProdutoModificado>();

		public ArrayList<ProdutoModificado> getTabelaFatura() {

			return tabelaFatura;

		}
		
		
		public static class ProdutoModificado{
			public ProdutoModificado() {
				super();
			}

			String nome;
			double Preco;
			double quantidade;
			double total;

			public ProdutoModificado(String nome, double quantidade, double preco, double total) {
				super();
				this.nome = nome;
				Preco = preco;
				this.quantidade = quantidade;
				this.total = total;
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

			public double getQuantidade() {
				return quantidade;
			}

			public void setQuantidade(double quantidade) {
				this.quantidade = quantidade;
			}

			public double getTotal() {
				return total;
			}

			public void setTotal(double total) {
				this.total = total;
			}
			
		}
		public String voltarMenu(){
			
			total=0.0;
			tabelaFatura.clear();
			
			
			total=0.0;
			
		return "/interna/MenuGerente.jsf";

		}
	
	
	
}