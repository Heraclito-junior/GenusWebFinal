package controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import dao.ForneceProdutoDAO;

import dao.VendaContemDAO;
import dao.VendaDAO;

import dominio.ForneceProduto;
import dominio.Fornecedor;
import ejb.faturaService;
import ejb.fornecedorService;


@ManagedBean
@SessionScoped
public class ForneceProdutoMB {
	
	public ForneceProdutoMB() {
		super();
		listaForneceProduto = new ArrayList<ForneceProduto>();
		idFornecimento="";
	}
	
	String idFornecimento;
	
	@EJB
	private fornecedorService fornecedorCervice;




	public String getIdFornecimento() {
		return idFornecimento;
	}

	public void setIdFornecimento(String idFornecimento) {
		this.idFornecimento = idFornecimento;
	}
	
	private List<ForneceProduto> listaForneceProduto;
	
	
	


	public List<ForneceProduto> getListaVendaContem() {
		return listaForneceProduto;
	}

	public void setListaVendaContem(List<ForneceProduto> listaVendaContem) {
		this.listaForneceProduto = listaVendaContem;
	}

	
	public void filtrar() {
		int resultado=fornecedorCervice.filtrarFornecedor(idFornecimento, listaForneceProduto);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Fornecedor não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
		}else if(resultado==1){
			setListaVendaContem(fornecedorCervice.detalhar(idFornecimento));
		}
		if(listaForneceProduto.size()==0){
			FacesMessage msg = new FacesMessage("Fornecedor não Fornece Nenhum produto");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}
		
	}
	

		
	
	public String voltar() {
		idFornecimento="";
		listaForneceProduto.clear();
		return "/interna/MenuGerente.jsf";
	}
	
	
	
	public String listar() {
		System.out.println("aqui");
		return "/interna/listaCategoria.jsf";
	}
}
