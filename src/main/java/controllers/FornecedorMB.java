package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dominio.Fornecedor;
import ejb.fornecedorService;

@ManagedBean
@SessionScoped
public class FornecedorMB {
	
	@EJB
	private fornecedorService fornecedorS;
	
	String fid;
	String nome;
	



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	private Fornecedor fornecedor;
	
	

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setCliente(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}



	
	private List<Fornecedor> listaFornecedor;
	

	public List<Fornecedor> getListaFornecedor() {
		setListaFornecedor(fornecedorS.listar());
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public FornecedorMB() {
		super();
		fid="";
		nome="";
	}	
	
	
	public void cadastrar() {
		
		int resultado=fornecedorS.cadastrar(nome);
		
		if(resultado==1){
			fornecedor = new Fornecedor();
			FacesMessage msg = new FacesMessage("Fornecedor Cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nome="";
			
		}else if(resultado==2){
			FacesMessage msg = new FacesMessage("Nome Ja Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nome="";
		}
		
	}
	
}
