package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dominio.Cliente;
import ejb.clienteService;

@ManagedBean
@SessionScoped
public class ClienteMB {
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	private Cliente cliente;
	
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@EJB
	private clienteService clienteCervice;



	
	private List<Cliente> listaCliente;
	
	String cpf;
	String nome;
	String data;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ClienteMB() {
		super();
		cpf="";
		nome="";
	}

	
	public List<Cliente> getlistaCategoria() {
		setListaCliente(clienteCervice.listar());
		return listaCliente;
	}
	

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
		
	
	
	public String filtrar() {
		
		String resultado=clienteCervice.filtrar(cpf,data);
		
		if(resultado.equalsIgnoreCase("0")){
			FacesMessage msg = new FacesMessage("Formato invalido data");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			data="";
			nome="";
			return null;

			
		}
		if(resultado.equalsIgnoreCase("1")){
			FacesMessage msg = new FacesMessage("Data não contem só dígitos");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			data="";
			nome="";
			return null;

			
		}
		if(resultado.equalsIgnoreCase("2")){
			FacesMessage msg = new FacesMessage("Mes não tem tantos dias");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			data="";
			nome="";
			return null;

			
		}
		if(resultado.equalsIgnoreCase("3")){
			FacesMessage msg = new FacesMessage("CPF muito Pequeno");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			//data="";
			//cpf="";
			nome="";
			return null;

			
		}
		if(resultado.equalsIgnoreCase("4")){
			FacesMessage msg = new FacesMessage("CPF e Data não casam");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			data="";
			nome="";
			return null;

			
		}
		if(resultado.equalsIgnoreCase("5")){
			FacesMessage msg = new FacesMessage("CPF Ja Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			data="";
			cpf="";
			nome="";
			return null;
		}
		
		
		nome=resultado;

		
		return null;
		
		
		
	}
	
	public String confirmar() {
		
		int resultado=clienteCervice.confirmar(cpf,data,nome);
		
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Cliente invalido");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}else{
			FacesMessage msg = new FacesMessage("Cliente Cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			cpf="";
			nome="";
			data="";
		}
		
		return null;
		
		
	}
	
	
	
	
	
	
	
	
	public String voltar() {
		cpf="";
		nome="";
		data="";
		return "/interna/MenuVendedor.jsf";
	}
	
	
	
	public String listar() {
		return "/interna/listaCategoria.jsf";
	}
}
