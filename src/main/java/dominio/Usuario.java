package dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {
	@Id
	@Column(nullable=false)
	private String login;
	
	@Column(nullable=false)
	private String senha;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="usuario_contato",
		joinColumns={@JoinColumn(name="usuario_id")},
		inverseJoinColumns={@JoinColumn(name="contato_id")})
	private List<Contato> contatos;
	
	
	public Usuario() {
		this.contatos = new ArrayList<Contato>();
	}
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
		this.contatos = new ArrayList<Contato>();
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
}
