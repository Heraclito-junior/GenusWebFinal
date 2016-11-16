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
@Table(name="Fornecedor")
public class Fornecedor {
	
	
	public Fornecedor(int fID, String fname) {
		super();
		FID = fID;
		Fname = fname;
	}



	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}








	public String getFname() {
		return Fname;
	}








	public void setFname(String fname) {
		Fname = fname;
	}








	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private int FID;
	
	@Column(nullable=false)
	private String Fname;
	
	
	
	
	
	
	
	
	public Fornecedor() {
	}
	
}
