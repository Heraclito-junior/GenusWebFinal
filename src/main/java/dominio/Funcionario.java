package dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Funcionario")
public class Funcionario {
	
	
	@Id
	@Column(nullable=false)
	private String id;
	
	
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String senha;
	/**
	@Column(nullable=false)
	private java.sql.Date dataNascimento;
	*/
	
	@Column(nullable=true)
	private String endereco;
	
	@Column(nullable=false)
	private double salario;
	
	@Column(nullable=false)
	private String telefone;
	
	@Column(nullable=false)
	private String tipoFuncionario;
	
	


	@Column(nullable=false)
	private boolean ativo;
	
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void setID(String iD) {
		id = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome2) {
		nome = nome2;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha2) {
		senha = senha2;
	}
/**
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento2) {
		dataNascimento = new java.sql.Date(dataNascimento2.getTime());
	}
	*/

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco2) {
		endereco = endereco2;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario2) {
		salario = salario2;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone2) {
		telefone = telefone2;
	}
	
	public String getTipoFuncionario() {
		return tipoFuncionario;
	}

	public void setTipoFuncionario(String tipoFuncionario2) {
		tipoFuncionario = tipoFuncionario2;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo2) {
		ativo = ativo2;
	}

	public Funcionario(String iD, String senha, String nome2, String endereco2, double salario2,
			String telefone2, String tipoFuncionario2) {
		super();
		id = iD;
		this.senha = senha;
		nome = nome2;
		
		endereco = endereco2;
		salario = salario2;
		telefone = telefone2;
		tipoFuncionario=tipoFuncionario2;
		ativo=true;
	}

	
	
	
	
	
	
	public Funcionario() {
	}
	
}
