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
@Table(name="Venda")
public class Venda {
	
	
	public Venda() {
		super();
	}



	public Venda(Funcionario iDvendedor, Cliente iDcliente, Double valorTotal ,String tipoDesconto) {
		super();
				IDvendedor = iDvendedor;
		IDcliente = iDcliente;
		ValorTotal = valorTotal;
		TipoPagamento = tipoDesconto;
	}
	
	

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public Funcionario getIDvendedor() {
		return IDvendedor;
	}

	public void setIDvendedor(Funcionario iDvendedor) {
		IDvendedor = iDvendedor;
	}
	
	

	public Cliente getIDcliente() {
		return IDcliente;
	}

	public void setIDcliente(Cliente iDcliente) {
		IDcliente = iDcliente;
	}

	public Double getValorTotal() {
		return ValorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		ValorTotal = valorTotal;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int idVenda;
	
	
	@OneToOne
	@JoinColumn(name="IDFunc")
	private Funcionario IDvendedor;
	
	@OneToOne
	@JoinColumn(name="CPF")
	private Cliente IDcliente;
	
	@Column(nullable=false)
	private Double ValorTotal;
	
	@Column(nullable=false)
	private String TipoPagamento;
	
	
	

	public String getTipoDesconto() {
		return TipoPagamento;
	}



	public void setTipoDesconto(String tipoDesconto) {
		TipoPagamento = tipoDesconto;
	}
	
	
	
	
	

	
}
