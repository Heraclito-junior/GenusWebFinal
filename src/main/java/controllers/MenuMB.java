package controllers;

//import java.util.ArrayList;

//import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

//import dao.ContatoDAO;
import dao.FuncionarioDAO;
import dominio.Contato;
import dominio.Funcionario;

@ManagedBean
@SessionScoped
public class MenuMB {



/**	
	public VendaMB getVendaM() {
		return vendaM;
	}

	public void setVendaM(VendaMB vendaM) {
		this.vendaM = vendaM;
	}
	*/

	public MenuMB(){
		
		//ctrluser=new DadosSistema();
		//vendaM=new VendaMB();
	}
	
	
	@ManagedProperty(value="#{dados}")
	private DadosSistema ctrluser;
	

	
	
	
	
	public DadosSistema getCtrluser() {
		return ctrluser;
	}

	public void setCtrluser(DadosSistema ctrluser) {
		this.ctrluser = ctrluser;
	}
	//@ManagedProperty(value="#{venda}")
	//private VendaMB vendaM;

	
	public String VoltarMenu() {
		Funcionario funcionario=new Funcionario();
		funcionario=ctrluser.recuperarUsuario();
		if(funcionario.getTipoFuncionario().equals("gerente")){
			return "/interna/MenuGerente.jsf";
		}
		else{
			return "/interna/MenuVendedor.jsf";
		}

	}
	
	public String RedirecionarCadastrar() {
		
		return "/interna/cadastraFunc.jsf";
	}
	public String RedirecionarCadastrarCategoria() {
		return "/interna/cadastraCategoria.jsf";
	}
	
	public String RedirecionarVenda() {
		return "/interna/cadastrarVenda.jsf";
	}
	public String RedirecionarCliente() {
		return "/interna/cadastraCliente.jsf";
	}
	public String RedirecionarCadastrarProduto() {
		return "/interna/cadastraProduto.jsf";
	}
	public String RedirecionarListaProduto() {
		return "/interna/listaProdutos.jsf";
	}
	public String RedirecionarCadastrarFuncionario() {
		return "/interna/cadastraFunc.jsf";
	}
	public String RedirecionarListaFuncionario() {
		return "/interna/listaFuncionario.jsf";
	}
	
	
	public String RedirecionarCadastrarFornecedor() {
		return "/interna/CadastraFornecedor.jsf";
	}
	public String RedirecionarFornecimento() {
		return "/interna/CadastrarFornecimento.jsf";
	}
	public String RedirecionarComecoFatura() {
		return "/interna/ComecarFatura.jsf";
	}
	public String RedirecionarAlterarProduto() {
		return "/interna/AlterarProduto.jsf";
	}
	
	public String RedirecionarListaVendas() {
		return "/interna/listaVendas.jsf";
	}
	
	public String RedirecionarListaFornecedor() {
		return "/interna/listaFornecedores.jsf";
	}
	public String RedirecionarDetalharVenda() {
		return "/interna/DetalharVenda.jsf";
	}
	public String RedirecionarDetalharFatura() {
		return "/interna/DetalharFatura.jsf";
	}
	
	public String RedirecionarListarFatura() {
		return "/interna/listaFatura.jsf";
	}
	public String DetalharFornecimento() {
		return "/interna/DetalharFornecimento.jsf";
	}
	public String RedirecionarListaCliente() {
		return "/interna/listarCliente.jsf";
	}
	
	public String Sair() {
		
		return "/login/login.jsf";
	}
}
