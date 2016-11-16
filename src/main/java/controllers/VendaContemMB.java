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
import dao.ProdutoDAO;
import dao.VendaContemDAO;
import dao.VendaDAO;
import dominio.Categoria;
import dominio.Contato;
import dominio.Fatura;
import dominio.Venda;
import dominio.VendaContem;
import ejb.fornecedorService;
import ejb.vendaService;

@ManagedBean
@SessionScoped
public class VendaContemMB {
	
	public VendaContemMB() {
		super();
		listaVendaContem = new ArrayList<VendaContem>();
		idVenda=0;
	}
	@EJB
	private vendaService vendaCervice;


	@Inject
	private VendaContemDAO vendaContemDao;
	@Inject
	private VendaDAO vendaDao;
	
	
	int idVenda;
	
	
	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}



	@Inject
	private VendaContemDAO vendaConteDao;
	
	private List<VendaContem> listaVendaContem;
	
	
	


	public List<VendaContem> getListaVendaContem() {
		//setlistaVendaContem(vendaConteDao.listar(idVenda));
		return listaVendaContem;
	}

	public void setListaVendaContem(List<VendaContem> listaVendaContem) {
		this.listaVendaContem = listaVendaContem;
	}

	
	public void filtrar() {
		int resultado=vendaCervice.filtrarVenda(idVenda);
		
		//Venda vendaTemp=vendaConteDao.buscarVendaID(idVenda);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Venda não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
		}else if(resultado==1){
			setListaVendaContem(vendaCervice.detalhar(idVenda));
		}
		if(listaVendaContem.size()==0){
			FacesMessage msg = new FacesMessage("Venda Sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}
		
		
		

		
	}
	
	public Venda retornaPreco() {
		
		Venda aux=new Venda();
		aux=vendaContemDao.buscarVendaID(idVenda);
		if(aux==null){
			return null;
		}
		
		
		
		return aux;
	}
		
	
	public String voltar() {
		idVenda=0;
		listaVendaContem.clear();
		return "/interna/MenuGerente.jsf";
	}
	
	
	
	public String listar() {
		System.out.println("aqui");
		return "/interna/listaCategoria.jsf";
	}
}
