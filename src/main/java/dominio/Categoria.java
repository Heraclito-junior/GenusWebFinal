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
@Table(name="Categoria")
public class Categoria {
	
	
	public Categoria() {
		super();
	}

	public Categoria( String nome2) {
		super();

		nome = nome2;
	}

	public int getIDcategoria() {
		return IDcategoria;
	}

	public void setIDcategoria(int iDcategoria) {
		IDcategoria = iDcategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome2) {
		nome = nome2;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int IDcategoria;
	
	@Column(nullable=false)
	private String nome;
	

	
	
	
	
}
