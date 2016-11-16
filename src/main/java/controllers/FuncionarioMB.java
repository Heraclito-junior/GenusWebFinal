package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dominio.Funcionario;
import ejb.SOAPClientSAAJ;
import ejb.funcionarioService;

@ManagedBean
@SessionScoped
public class FuncionarioMB {
	private Funcionario funcionario;
	
	@EJB
	private funcionarioService funcionarioservice;
	
	
	@EJB
	private SOAPClientSAAJ soap;
	
	@ManagedProperty(value="#{dados}")
	private DadosSistema ctrluser;
	
	
	public DadosSistema getCtrluser() {
		return ctrluser;
	}

	public void setCtrluser(DadosSistema ctrluser) {
		this.ctrluser = ctrluser;
	}








	private List<Funcionario> listaFunc;
	
	private double salarioParse;
	
	
	public double getSalarioParse() {
		return salarioParse;
	}

	public void setSalarioParse(double salarioParse) {
		this.salarioParse = salarioParse;
	}

	public FuncionarioMB() {
		funcionario = new Funcionario();
		
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcio) {
		this.funcionario = funcio;
	}
	

	public List<Funcionario> getListaFuncionario() {
		setListaFuncionario(funcionarioservice.listar());
		return listaFunc;
	}

	
	public void setListaFuncionario(List<Funcionario> listaFunc) {
		this.listaFunc = listaFunc;
	}
	
	
	public void cadastrar() {
		System.out.println("usando novo sistema");
		int resultado=funcionarioservice.cadastrarFuncionario(funcionario.getID(), funcionario.getNome(), funcionario.getSenha(), funcionario.getEndereco(),salarioParse, funcionario.getTelefone(), funcionario.getTipoFuncionario());
		if(resultado==3){
			FacesMessage msg = new FacesMessage("Digite uma quantia maior que 0 para o salario");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		if(resultado==1){
			funcionario = new Funcionario();
			salarioParse=0.0;
			FacesMessage msg = new FacesMessage("Funcionario Cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Login já existe");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;			
		}
		
		
	}
	
	
	
	
	
	
	
	
	public String login() {
		//soap.iniciar();
		int resultado=funcionarioservice.login(funcionario.getID(),funcionario.getSenha());
		if(resultado==0){
			FacesMessage msg = new FacesMessage("Funcionario bloqueado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		if(resultado==1){
			FacesMessage msg = new FacesMessage("Senha incorreta");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		if(resultado==2){
			FacesMessage msg = new FacesMessage("Funcionario nao encontrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		if(resultado==3){
			ctrluser.mudarUsuario(funcionario.getID());
			ctrluser.setTelaAnterior("MenuGerente");
			funcionario=new Funcionario();
			return "/interna/MenuGerente.jsf";
		}
		if(resultado==4){
			ctrluser.mudarUsuario(funcionario.getID());
			ctrluser.setTelaAnterior("MenuVendedor");
			funcionario=new Funcionario();
			return "/interna/MenuVendedor.jsf";
		}
		return null;

	}
}
