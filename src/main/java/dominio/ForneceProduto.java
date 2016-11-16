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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ForneceProduto")
public class ForneceProduto {
	
	
	

	public ForneceProduto(Fornecedor fid, Produto produtoFatura, Double precoNoMomento) {
		super();
		this.fid = fid;
		this.produtoFornecimento = produtoFatura;
		PrecoNoMomento = precoNoMomento;
	}

	public int getIdForneceProdutoProprio() {
		return idForneceProdutoProprio;
	}

	public void setIdForneceProdutoProprio(int idVendaContemProprio) {
		this.idForneceProdutoProprio = idVendaContemProprio;
	}

	public Fornecedor getFid() {
		return fid;
	}

	public void setFid(Fornecedor fid) {
		this.fid = fid;
	}



	public Double getPrecoNoMomento() {
		return PrecoNoMomento;
	}

	public void setPrecoNoMomento(Double precoNoMomento) {
		PrecoNoMomento = precoNoMomento;
	}

	public ForneceProduto() {
		super();
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int idForneceProdutoProprio;

	
	@ManyToOne
	@JoinColumn(name="FID")
	private Fornecedor fid;
	
	@ManyToOne
	@JoinColumn(name="idProduto")
	private Produto produtoFornecimento;
	

	public Produto getProdutoFornecimento() {
		return produtoFornecimento;
	}

	public void setProdutoFornecimento(Produto produtoFornecimento) {
		this.produtoFornecimento = produtoFornecimento;
	}


	@Column(nullable=false)
	private Double PrecoNoMomento;

	
	
	
}