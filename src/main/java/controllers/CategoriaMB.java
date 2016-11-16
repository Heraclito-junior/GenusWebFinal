package controllers;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriaDAO;

import dominio.Categoria;
import dominio.Contato;
import ejb.SOAPClientSAAJ;
import ejb.categoriaService;

@ManagedBean
@SessionScoped
public class CategoriaMB {
	private Categoria categoria;
	
	@EJB
	private categoriaService categoriaservice;
	
	@EJB
	private SOAPClientSAAJ soap;
	
	private List<Categoria> listaCategoria;
	
	
	


	public CategoriaMB() {
		categoria = new Categoria();
		listaCategoria = new ArrayList<Categoria>(); 
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

	public List<Categoria> getlistaCategoria() {
		
		
		
		setListaCategoria(categoriaservice.listarCategoria());
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCatego) {
		this.listaCategoria = listaCatego;
	}
		
	
	
	public void cadastrar() {
		System.out.println("aqui");
		//soap.iniciar();
		
		int resultado=categoriaservice.cadastrarCategoria(categoria);
		categoria = new Categoria();
		if(resultado==1){
			
			FacesMessage msg = new FacesMessage("Categoria Cadastrada");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			categoria=new Categoria();
			return;
			
		}else{
			FacesMessage msg = new FacesMessage("Categoria Ja Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		//return "/interna/listaCategoria.jsf";
	}
	
	public String voltar() {
		return "/interna/cadastraCategoria.jsf";
	}
	
	
	
	public String listar() {
		return "/interna/listaCategoria.jsf";
	}
}
