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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Produto")
public class Produto {
	
	



	public int getIdProduto() {
		return idProduto;
	}


	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}


	public double getQuantidade() {
		return Quantidade;
	}


	public void setQuantidade(double quantidade) {
		Quantidade = quantidade;
	}


	public double getPreco() {
		return Preco;
	}


	public void setPreco(double preco) {
		Preco = preco;
	}


	public String getNome() {
		return Nome;
	}


	public void setNome(String nome) {
		Nome = nome;
	}


	public Categoria getIDcateg() {
		return Categ;
	}


	public void setIDcateg(Categoria iDcateg) {
		Categ = iDcateg;
	}


	public Produto() {
		super();
	}


	public Produto(double quantidade, double preco, String nome, Categoria iDcategoria) {
		super();
		Quantidade = quantidade;
		Preco = preco;
		Nome = nome;
		Categ = iDcategoria;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int idProduto;
	
	@Column(nullable=false)
	private double Quantidade;
	
	@Column(nullable=false)
	private double Preco;
	
	
	@Column(nullable=false)
	private String Nome;
	
	
	@OneToOne
	@JoinColumn(name="IDcategoria")
	private Categoria Categ;
	
	@Column(nullable=false)
	private boolean Fracionavel;

	public boolean isFracionavel() {
		return Fracionavel;
	}


	public void setFracionavel(boolean fracionavel) {
		Fracionavel = fracionavel;
	}


	
	
	
	
}
