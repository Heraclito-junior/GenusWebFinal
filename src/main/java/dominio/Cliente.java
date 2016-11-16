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
@Table(name="Cliente")
public class Cliente {
	
	
	public Cliente() {
		super();
	}

	public Cliente(Long cPF,String data, String nome) {
		super();
		CPF = cPF;
		Data = data;
		Nome = nome;
	}

	public long getCPF() {
		return CPF;
	}

	public void setCPF(long cPF) {
		CPF = cPF;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	@Id
	@Column(nullable=false)
	private Long CPF;
	
	@Column(nullable=false)
	private String Data;
	
	@Column(nullable=false)
	private String Nome;

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}
	
	
	
}
