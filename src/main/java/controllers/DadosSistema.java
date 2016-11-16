package controllers;
import java.util.ArrayList;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;


import dao.FuncionarioDAO;
import dominio.Cliente;
import dominio.Contato;

import dominio.Fornecedor;
import dominio.Funcionario;

@ManagedBean(name="dados")
@SessionScoped
public class DadosSistema {
	

	@Inject
    FuncionarioDAO ctrltemp;
    List<String> strings = new ArrayList<String>();
    int idVendaRecente;
    String idFuncionario;
    String idFornecedor;
    
    public String getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(String idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	String telaAnterior;
    
    
  

    
    
    
    public String getTelaAnterior() {
		return telaAnterior;
	}

	public void setTelaAnterior(String telaAnterior) {
		this.telaAnterior = telaAnterior;
	}

	public int getIdVendaRecente() {
		return idVendaRecente;
	}

	public void setIdVendaRecente(int idVendaRecente) {
		this.idVendaRecente = idVendaRecente;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
    public List<String> getStrings() {
        return strings;
    }

    public void createNewTile() {
        strings.add("output");
    }
    public void removeNewTile() {
        strings.remove("output");
    }
    
    
    
    public void mudarUsuario(String string){
    	idFuncionario=string;
    }
    public void mudarFornecedor(String string){
    	idFornecedor=string;
    }
    
    public Funcionario recuperarUsuario(){
    	
    	
    	Funcionario funcTemp=ctrltemp.buscarFuncionarioID(idFuncionario);
    	return funcTemp;
    }

	




	

    
}