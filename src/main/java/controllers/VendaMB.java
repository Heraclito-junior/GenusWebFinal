package controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

//import java.util.ArrayList;

//import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dominio.Cliente;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;
import ejb.vendaService;

@ManagedBean
@SessionScoped
public class VendaMB {
	@ManagedProperty(value="#{dados}")
	private DadosSistema ctrluser;
	
	public DadosSistema getCtrluser() {
		return ctrluser;
	}

	public void setCtrluser(DadosSistema ctrluser) {
		this.ctrluser = ctrluser;
	}
	
	
	
	@EJB
	private vendaService vendaCervice;
	private List<Venda> listaVenda;
	
	public List<Venda> getListaVenda() {
		setListaVenda(vendaCervice.listar());
		return listaVenda;
	}

	
	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
	private Venda nVenda;
	public Venda getnVenda() {
		return nVenda;
	}

	public void setnVenda(Venda nVenda) {
		this.nVenda = nVenda;
	}





	long idClientela;
	
	public long getIdClientela() {
		return idClientela;
	}

	public void setIdClientela(long idClientela) {
		this.idClientela = idClientela;
	}

	String nomeCategoria;
	Cliente dud;


	Double total;
	String nomeProdCarrinho;
	double quntProdCarrinho;
	
	List<String> strings = new ArrayList<String>();
	
	public List<String> getStrings() {
		return strings;
	}

	public void setStrings(List<String> strings) {
		this.strings = strings;
	}




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
	
	
	public VendaMB() {
		ctrluser=new DadosSistema();
		nVenda=new Venda();
		new ArrayList<Produto>(); 

		total=0.0;
		idClientela=1;
		
	}
		
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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
	
	
	
	

	public void adicionarAoCarrinho() {
		
		int resultado=vendaCervice.adicionarAoCarrinho(nomeProdCarrinho, quntProdCarrinho, tabelaVenda);
		
		System.out.println("ejb venda add");
		
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Coloque nome para produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
		}else if(resultado==1){
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}else if(resultado==2){
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}else if(resultado==3){
			FacesMessage msg = new FacesMessage("Produto não Fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;

			
		}else if(resultado==4){
			Double quantidadeAtual=vendaCervice.buscarQuantidadeEstoque(nomeProdCarrinho);
			FacesMessage msg = new FacesMessage("Quantidade insuficiente no estoque, o maximo é "+ quantidadeAtual);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;

			
		}
		
		
		
		
		
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		total=vendaCervice.getTotal();		
		
		
		return; 
	}
	
	public void remover() {
		
		int resultado=vendaCervice.remover(nomeProdCarrinho, quntProdCarrinho, tabelaVenda);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Coloque um nome para produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}else if(resultado==1){
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;

			return;
			
		}else if(resultado==2){
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			return;
			
		}else if(resultado==3){
			FacesMessage msg = new FacesMessage("Produto não está no carrinho");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeProdCarrinho="";
			quntProdCarrinho=0.0;

			return;
			
		}else if(resultado==4){
			FacesMessage msg = new FacesMessage("Produto não Fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;
			
		}else if(resultado==5){
			FacesMessage msg = new FacesMessage("Não há tanto produto no carrinho para remover");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
		}
		
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			total=vendaCervice.getTotal();		
			
			
			
			
			return; 
			
		}		
		
	
	
	public String finalizarAsCompras() {
		Funcionario VendedorTemp=ctrluser.recuperarUsuario();
		System.out.println(VendedorTemp.getNome());
		int resultado=vendaCervice.finalizarAsCompras(VendedorTemp, idClientela, tabelaVenda,nVenda.getTipoDesconto());
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Venda sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		
		vendaCervice.limpar();
		tabelaVenda.clear();
		nVenda=new Venda();
		total=0.0;
		idClientela=1;		
		return "/interna/sucesso.jsf";
	}
	public void limpar() {
		vendaCervice.limpar();
		tabelaVenda.clear();
		total=0.0;

	}
	
	public String voltar() {
		vendaCervice.limpar();
		tabelaVenda.clear();
		//strings.clear();
		total=0.0;
		
		return "/interna/MenuVendedor.jsf";
		
		
	}

	private static final ArrayList<ProdutoModificado> tabelaVenda =
			new ArrayList<ProdutoModificado>();

		public ArrayList<ProdutoModificado> getTabelaVenda() {

			return tabelaVenda;

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
		
		public String voltarAVenda(){
			return "/interna/cadastrarVenda.jsf";
		}
		public String listaProdutos(){
			return "/interna/listaProdutosVenda.jsf";
		}
		public String cadastroCliente(){
			return "/interna/cadastraClienteVenda.jsf";
		}
		
		
	
	
	
}