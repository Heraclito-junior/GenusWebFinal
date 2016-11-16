package ejb;

import java.text.DecimalFormat;
import java.util.List;



import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import controllers.DadosSistema;
import dao.CategoriaDAO;
import dao.FuncionarioDAO;
import dao.ProdutoDAO;
import dominio.Categoria;
import dominio.Funcionario;
import dominio.Produto;


@Stateless
public class produtoService {
	
	
	
	
	
	@Inject 
	ProdutoDAO produtoDAO;
	@Inject
	private CategoriaDAO categDao;
	
	public double truncarValor(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public double truncarQuantidade(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.###");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int cadastrar(String nomeCategoria, Produto produto ) {
		Categoria categoriaAuxiliar=new Categoria();
		categoriaAuxiliar=produtoDAO.retornaCategoria(nomeCategoria);
		if(produto.getPreco()<=0.0){
			
			return 0;
			
		}
		if(categoriaAuxiliar==null){
			
			return 1;
		}
		double verificacaoQuantidadeFracionavel=produto.getQuantidade();
		
		if(verificacaoQuantidadeFracionavel% 1 != 0 &&produto.isFracionavel()==false){
			
			return 2;
		}
		
		
		double trucarvalor=truncarValor(produto.getPreco());
		
		produto.setPreco(trucarvalor);
		
		double trucarQuantidade=truncarQuantidade(produto.getQuantidade());
		
		produto.setQuantidade(trucarQuantidade);
		
		
		produto.setIDcateg(categoriaAuxiliar);
		
		
		
		
		Produto c = produtoDAO.buscarProdutoNome(produto.getNome());
		
		if (c == null) {
			produtoDAO.salvar(produto);
		} else {
			
			return 3;
		}
		
		return 4;
	}
	
	
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int alterar(String produtoNA,String produtoN, Double produtoP,String nomeCategoria,boolean mudarpreco){
		
		
		Categoria categoria=new Categoria();
		Produto temp=produtoDAO.buscarProdutoNome(produtoNA);
		if(produtoP!=0.0&&mudarpreco==false){
			
			return 0;
		}
		if(temp==null){
			
			return 1;
		}
		if(produtoP<=0.0&&mudarpreco==true){
			
			return 2;
		}
		if(!nomeCategoria.equals("")){
			categoria=categDao.buscarCategoriaNome(nomeCategoria);
			if(categoria==null){
				
				return 3;
			}
		}
		
		produtoP=truncarValor(produtoP);		
		
		
		
		
		
		if(!produtoN.equals("")){
			temp.setNome(produtoN);
		}
		
		if(!nomeCategoria.equals("")){
			temp.setIDcateg(categoria);
		}
		if(!produtoN.equals("") && produtoP==0.0 && !nomeCategoria.equals("")){
			
			return 4;
		}
		
		
		
		
		produtoDAO.atualizar(temp);
		
		
		return 5;
		
		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Produto> listar(){
		
		
		
		return produtoDAO.listar();
		

	}
	
	
	
	
	
	
	
	
	
	
}