package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dominio.Fatura;
import dominio.FaturaReferenteProduto;
import ejb.faturaService;

@ManagedBean
@SessionScoped
public class FaturaContemMB {
	@EJB
	private faturaService faturaservice;
	public FaturaContemMB() {
		super();
		listaFaturaContem = new ArrayList<FaturaReferenteProduto>();
		idFatura=0;
	}



	
	

	int idFatura;
	
	




	public int getIdFatura() {
		return idFatura;
	}

	public void setIdFatura(int idFatura) {
		this.idFatura = idFatura;
	}


	
	private List<FaturaReferenteProduto> listaFaturaContem;
	
	
	


	public List<FaturaReferenteProduto> getListaFaturaContem() {
		return listaFaturaContem;
	}

	public void setListaFaturaContem(List<FaturaReferenteProduto> listaFaturaContem) {
		this.listaFaturaContem = listaFaturaContem;
	}


	
	
	
	
	public void filtrar() {
		List<FaturaReferenteProduto> listaFaturaContem2;
		
		//listaFaturaContem2=new ArrayList<FaturaReferenteProduto>();
		
		int resultado=faturaservice.filtrarFatura(idFatura,listaFaturaContem);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Fatura não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			idFatura=0;
			return;
		}
		if(resultado==1){
			
			FacesMessage msg = new FacesMessage("Fatura sem Produtos");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			idFatura=0;
			return;
		}
		
		
		if(resultado==2){
			//System.out.println("tambem");
			//System.out.println(listaFaturaContem.get(0).getPrecoNaHora());
			setListaFaturaContem(faturaservice.detalhar(idFatura));
			
			//setListaFaturaContem(listaFaturaContem2);
			return;
		}
		
		
		
	}
	
	public Fatura retornaPreco() {
		Fatura aux=new Fatura();
		int resultado=faturaservice.retornarPreco(idFatura,aux);
		
		if(resultado==0){
			return null;
		}	
		aux=faturaservice.retoranarAUX(idFatura);

		return aux;
	}
		
	
	public String voltar() {
		idFatura=0;
		listaFaturaContem.clear();
		return "/interna/MenuGerente.jsf";
	}
	
	
	
	public String listar() {
		return "/interna/listaCategoria.jsf";
	}
}
