package dominio;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="VendaContem")
public class VendaContem {
	
	@ManyToOne
	@JoinColumn(name="idProduto")
	private Produto nomeProduto;
	//idVenda
	
	@ManyToOne
	@JoinColumn(name="idVenda")
	private Venda venda_id;
	
	
	
	
	
	
	public Produto getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(Produto nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Venda getVenda_id() {
		return venda_id;
	}

	public void setVenda_id(Venda venda_id) {
		this.venda_id = venda_id;
	}
	
	

	public int getIdVendaContemProprio() {
		return idVendaContemProprio;
	}

	public void setIdVendaContemProprio(int idVendaContemProprio) {
		this.idVendaContemProprio = idVendaContemProprio;
	}

	public Double getQuantidadeVenda() {
		return quantidadeVenda;
	}

	public void setQuantidadeVenda(Double quantidadeVenda) {
		this.quantidadeVenda = quantidadeVenda;
	}

	public Double getPrecoNoMomento() {
		return PrecoNoMomento;
	}

	public void setPrecoNoMomento(Double precoNoMomento) {
		PrecoNoMomento = precoNoMomento;
	}

	public VendaContem() {
		super();
	}
	/**
	public VendaContem( Venda idVendaDoCont, List<Produto> idProdutos, int iDcliente,
			Double quantidadeVenda, String precoNoMomento) {
		super();
		this.idVendaDoCont = idVendaDoCont;
		this.idProdutos = idProdutos;
		IDcliente = iDcliente;
		this.quantidadeVenda = quantidadeVenda;
		PrecoNoMomento = precoNoMomento;
	}
	*/

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int idVendaContemProprio;



	
	@Column(nullable=true)
	private Double quantidadeVenda;
	
	@Column(nullable=true)
	private Double PrecoNoMomento;

	
}