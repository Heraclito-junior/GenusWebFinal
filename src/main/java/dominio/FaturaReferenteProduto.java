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
@Table(name="FaturaReferenteProduto")
public class FaturaReferenteProduto{
	
	


	
	public FaturaReferenteProduto() {
		super();
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int idFaturaContemProprio;



	
	@Column(nullable=true)
	private Double quantidadeFatura;
	
	@Column(nullable=true)
	private Double PrecoNaHora;
	
	@ManyToOne
	@JoinColumn(name="idProduto")
	private Produto produtofaturaid;
	

	@ManyToOne
	@JoinColumn(name="IDFat")
	private Fatura faturaReferenteid;


	public int getIdFaturaContemProprio() {
		return idFaturaContemProprio;
	}


	public void setIdFaturaContemProprio(int idFaturaContemProprio) {
		this.idFaturaContemProprio = idFaturaContemProprio;
	}


	public Double getQuantidadeFatura() {
		return quantidadeFatura;
	}


	public void setQuantidadeFatura(Double quantidadeFatura) {
		this.quantidadeFatura = quantidadeFatura;
	}


	public Double getPrecoNaHora() {
		return PrecoNaHora;
	}


	public void setPrecoNaHora(Double precoNaHora) {
		PrecoNaHora = precoNaHora;
	}


	public Produto getProdutofaturaid() {
		return produtofaturaid;
	} 


	public void setProdutofaturaid(Produto produtofaturaid) {
		this.produtofaturaid = produtofaturaid;
	}


	public Fatura getFaturaReferenteid() {
		return faturaReferenteid;
	}


	public void setFaturaReferenteid(Fatura faturaReferenteid) {
		this.faturaReferenteid = faturaReferenteid;
	}



	
}