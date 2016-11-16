package controllers;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ejb.faturaService;

@ManagedBean
@ViewScoped
public class comecarMB {

	@EJB
	private faturaService faturaservice;

	@ManagedProperty(value="#{dados}")
	private DadosSistema ctrluser;
	
	public DadosSistema getCtrluser() {
		return ctrluser;
	}

	public void setCtrluser(DadosSistema ctrluser) {
		this.ctrluser = ctrluser;
	}
	
	String fornecedor;
	
	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	
	


	public comecarMB() {
		fornecedor="";
	}
	
	
	
	
	public String mudarFornecedor() {
		int resultado=faturaservice.mudarFornecedor(fornecedor);
						
		if (resultado == 0) {
			FacesMessage msg = new FacesMessage("Fornecedor Não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
			return null;
		}
		ctrluser.mudarFornecedor(fornecedor);
		
		return "/interna/CadastrarFatura.jsf";
	}
	


	
}
