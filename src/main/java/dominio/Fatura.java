package dominio;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Fatura")
public class Fatura {
	
	
	public Fatura() {
		super();
	}

	public Fatura( Double valorTotal, Funcionario iDFunc, Fornecedor fID) {
		super();
		
		ValorTotal = valorTotal;
		;
		IDFunc = iDFunc;
		//FID = fID;
	}

	public int getIDFat() {
		return IDFat;
	}

	public void setIDFat(int iDFat) {
		IDFat = iDFat;
	}

	public Double getValorTotal() {
		return ValorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		ValorTotal = valorTotal;
	}



	public Funcionario getIDFunc() {
		return IDFunc;
	}

	public void setIDFunc(Funcionario iDFunc) {
		IDFunc = iDFunc;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int IDFat;
	
	@Column(nullable=false)
	private Double ValorTotal;
	
	
	@OneToOne
	@JoinColumn(name="IDFunc")
	private Funcionario IDFunc;
	
	@OneToOne
	@JoinColumn(name="FID")
	private Fornecedor fid;

	public Fornecedor getFid() {
		return fid;
	}

	public void setFid(Fornecedor fid) {
		this.fid = fid;
	}


	
}
