package ejb;

import java.util.List;



import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import controllers.DadosSistema;
import dao.CategoriaDAO;
import dao.FuncionarioDAO;
import dominio.Categoria;
import dominio.Funcionario;


@Stateless
public class funcionarioService {
	
	
	
	
	
	@Inject 
	FuncionarioDAO funcionarioDAO;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int cadastrarFuncionario(String login, String nome, String senha,String endereco, double salario, String telefone, String tipoFuncionario){
		System.out.println(salario);
		if(salario<=0){
			
				
				return 3;
				
			
		}
		
		Funcionario f= funcionarioDAO.buscarFuncionarioID(nome);
		Funcionario novoFunc=new Funcionario(login,senha,nome,endereco,salario,telefone,tipoFuncionario);
		novoFunc.setAtivo(true);
		
		if (f == null) {
			funcionarioDAO.salvar(novoFunc);
			
			
			return 1;
		} else {
			
			return 0;
		}
		
		
		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int login(String login, String senha){
		
		Funcionario temp=funcionarioDAO.cadastrarPrimeiro();
		
		if(temp==null){
			Funcionario primeiroGerente=new Funcionario("a","a","Gerente Original","Natal",10000,"93543344","gerente");
			funcionarioDAO.salvar(primeiroGerente);
		}
		
		Funcionario u = funcionarioDAO.buscarFuncionarioID(login);
		if (u != null) {
			if (u.getSenha().equals(senha)) {
				if(!u.isAtivo()){
					return 0;
				}				
				if(u.getTipoFuncionario().equals("gerente")){
					
					return 3;
				}else{
					
					return 4;
				}
				
			} else {
				FacesMessage msg = new FacesMessage("Senha incorreta");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				return 1;
			}
		} else {
			FacesMessage msg = new FacesMessage("Funcionario nao encontrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return 2;
		}
		
		
		
		

	}
	
	public List<Funcionario> listar(){
		return funcionarioDAO.listar();
	}
	
	
	
	
}