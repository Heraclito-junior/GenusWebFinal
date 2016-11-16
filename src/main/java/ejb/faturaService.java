package ejb;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import controllers.FaturaMB.ProdutoModificado;
import dao.FaturaContemDAO;
import dao.FaturaDAO;
import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dao.FuncionarioDAO;
import dao.ProdutoDAO;
import dominio.Fatura;
import dominio.FaturaReferenteProduto;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Funcionario;
import dominio.Produto;

@Stateless
public class faturaService {
	
	

	private List<Fatura> listaFatura;
	
	
	public Fatura getnFatura() {
		return nFatura;
	}

	public void setnFatura(Fatura nFatura) {
		this.nFatura = nFatura;
	}

	public faturaService() {
		super();
		
		nomeProdutos=new ArrayList<String>();
		quantidadeProdutos=new ArrayList<Double>();
		PrecoProdutos=new ArrayList<Double>();
		total=0.0;
		nFatura=new Fatura();
		
	}

	@Inject
	private FornecedorDAO fonecedorDAO;
	
	@Inject
	private FuncionarioDAO funcionarioDAO;
	
	@Inject
	private ProdutoDAO produtoDAO;
	
	@Inject
	private FaturaDAO faturaDAO;
	
	@Inject
	private FaturaContemDAO faturaContemDao;
	
	@Inject
	private ForneceProdutoDAO forneceProdutoDao;
	
	private Fatura nFatura;
	
	List<String> nomeProdutos;
	List<Double> quantidadeProdutos;
	List<Double> PrecoProdutos;
	Double total;
	
	public List<String> getNomeProdutos() {
		return nomeProdutos;
	}

	public void setNomeProdutos(List<String> nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}

	public List<Double> getQuantidadeProdutos() {
		return quantidadeProdutos;
	}

	public void setQuantidadeProdutos(List<Double> quantidadeProdutos) {
		this.quantidadeProdutos = quantidadeProdutos;
	}

	public List<Double> getPrecoProdutos() {
		return PrecoProdutos;
	}

	public void setPrecoProdutos(List<Double> precoProdutos) {
		PrecoProdutos = precoProdutos;
	} 
	
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int mudarFornecedor(String fornecedor){
		
		Fornecedor c = fonecedorDAO.buscarFornecedorNome(fornecedor);
		
		if (c == null) {

			return 0;
		}
		return 1;
		
		
		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int adicionarAoCarrinho(String  fornecedor, String nomeProdCarrinho, double quntProdCarrinho,ArrayList<ProdutoModificado> tabelaFatura ){
		
		Fornecedor temp=new Fornecedor();
		
		temp=fonecedorDAO.buscarFornecedorNome(fornecedor);

		if(nomeProdCarrinho.equals("")){
			
			return 0;
		}
		if(quntProdCarrinho<=0){
			
			
			return 1;
		}
		
		Produto novoProduto=new Produto();
		
		novoProduto=produtoDAO.buscarProdutoNome(nomeProdCarrinho);

		
		if(novoProduto==null){
			
			
			return 2;
			
		}
		ForneceProduto fornecimento_temporario=new ForneceProduto();
		
		fornecimento_temporario=forneceProdutoDao.existeComb(temp, novoProduto);
		
		if(fornecimento_temporario==null){
			
			
			return 3;
			
		}
		
		
		
		String tempNome=novoProduto.getNome();
		int swit=-1;
		
		
		if(quntProdCarrinho % 1 != 0 && novoProduto.isFracionavel()==false){
			
			return 4;
		}
		
		
		
		
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
		}
		
		if(swit!=-1){
			Double auxiliar=(Double) quantidadeProdutos.get(swit);
			quntProdCarrinho=quntProdCarrinho+auxiliar;
		}
		
		
		
		
		if(swit==-1){
		nomeProdutos.add(nomeProdCarrinho);
		quantidadeProdutos.add(quntProdCarrinho);
		PrecoProdutos.add(fornecimento_temporario.getPrecoNoMomento());
		}else{
			quantidadeProdutos.set(swit, quntProdCarrinho);
			PrecoProdutos.set(swit,fornecimento_temporario.getPrecoNoMomento());
			
			
		}
		total=0.0;
		tabelaFatura.clear();
		
		
		for(int j=0;j<nomeProdutos.size();j++){
			
			double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
			
			double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
			double arredondarTotal=truncarValor(ValorTotalAnterior);
			total=total+arredondarTotal;
			int resultado2=addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondarTotal,tabelaFatura,nomeProdCarrinho);
			
			if(resultado2==0){
				return 5;
			}
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		total=truncarValor(total);
		
		
		
		return 6; 
		
		
		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int remover( String nomeProdCarrinho, double quntProdCarrinho,ArrayList<ProdutoModificado> tabelaFatura ){
		
		
		
		
		if(nomeProdCarrinho.equals("")){
			
			return 0;
		}
		if(quntProdCarrinho<=0){
			
			
			return 1;
		}
		
		
		Produto novoProduto=new Produto();
		novoProduto=produtoDAO.buscarProdutoNome(nomeProdCarrinho);
		
		
		
		if(novoProduto==null){
			
			
			return 2;
			
		}
		
		String tempNome=novoProduto.getNome();
		
		
		if(nomeProdutos.contains(tempNome)){
		}else{
			
			return 3;				
		}
		
		if(quntProdCarrinho % 1 != 0 && novoProduto.isFracionavel()==false){
			
			return 4;
		}
		
		
		

		
		int swit=-1;
		double auxiliarTansformacao;
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
			
			if(quantidadeProdutos.get(swit)<quntProdCarrinho){
				
				return 5;				
			}
			auxiliarTansformacao=quantidadeProdutos.get(swit);
			if(quntProdCarrinho==auxiliarTansformacao){
				nomeProdutos.remove(swit);
				quantidadeProdutos.remove(swit);
				PrecoProdutos.remove(swit);	
			}else{
				quantidadeProdutos.set(swit, auxiliarTansformacao-quntProdCarrinho);
			}
			
			
			
			
			total=0.0;
			tabelaFatura.clear();
			for(int j=0;j<nomeProdutos.size();j++){
				
				
				
				double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
				double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
				double arredondar=truncarValor(ValorTotalAnterior);
				
				total=total+arredondar;
				addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondar,tabelaFatura,nomeProdCarrinho);
				
				
			}
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			total=truncarValor(total);
			
			
			
			
			return 6; 
			
		}
		
		
		
		
		return 7;
		
		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int finalizarAsCompras(String  fornecedor, String nomeProdCarrinho, double quntProdCarrinho,ArrayList<ProdutoModificado> tabelaFatura,String vendedor){
		
		
		
		

		
		
		
		if(total==0.0){
			FacesMessage msg = new FacesMessage("Venda sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return 0;
		}
		Produto produtoTemp=new Produto();
		//System.out.println(idClientela);
		
		Fornecedor fornecedorTemp= new Fornecedor();
		double auxiliararitmetico;
		
		
		//Fornecedor fornecedorTempo=new Fornecedor();
		//fornecedorTempo=fonecedorDAO.buscarFornecedorNome(fornecedor);
		//clienteTemp=clienteDAO.buscarClienteCPF(idClientela);
		fornecedorTemp=fonecedorDAO.buscarFornecedorNome(fornecedor);
		
		Funcionario funcTemp=new Funcionario();
		funcTemp=funcionarioDAO.buscarFuncionarioID(vendedor);
		
		
		
		
		nFatura.setIDFunc(funcTemp);
		nFatura.setValorTotal(total);
		nFatura.setFid(fornecedorTemp);
		nFatura.setValorTotal(total);

		
		
		faturaDAO.salvar(nFatura);
		
		
		for(int i=0;i<nomeProdutos.size();i++){
			
			//ForneceProduto fornecimento_temporario=new ForneceProduto();
			
			//fornecimento_temporario=forneceProdutoDao.existeComb(fornecedorTemp, produto);
			
			Produto aux=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			
			FaturaReferenteProduto FaturaContemTEMP=new FaturaReferenteProduto();
			
			FaturaContemTEMP.setFaturaReferenteid(nFatura);
			
			//FaturaContemTEMP.setFaturaReferenteid(nFatura.getIDFat());
			FaturaContemTEMP.setProdutofaturaid(aux);
			FaturaContemTEMP.setPrecoNaHora(PrecoProdutos.get(i));
			FaturaContemTEMP.setQuantidadeFatura(quantidadeProdutos.get(i));
			
			
			produtoTemp=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			auxiliararitmetico=produtoTemp.getQuantidade()+quantidadeProdutos.get(i);
			produtoTemp.setQuantidade(auxiliararitmetico);
			produtoDAO.atualizar(produtoTemp);

			faturaContemDao.salvar(FaturaContemTEMP);
		}
		
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		nFatura=new Fatura();
		total=0.0;
		
		
		total=0.0;
		tabelaFatura.clear();

		
		return 2;
	}
	
	public double truncarQuantidade(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.###");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public double truncarValor(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public int addAction(String nome, double quantidade, double preco, double total,ArrayList<ProdutoModificado> tabelaFatura,String nomeProdCarrinho) {

		//Produto order = new Produto(quntProdCarrinho,);
		Produto order=produtoDAO.buscarProdutoNome(nomeProdCarrinho);
		if(order==null){
			
							
			return 0;
		}
		
		ProdutoModificado auxiliarDataTable=new ProdutoModificado(nome,quantidade,preco,total);
		tabelaFatura.add(auxiliarDataTable);
		return 1;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public List<Fatura> listar() {
		
		return faturaDAO.listar();
	}
	
	public int filtrarFatura(int idFatura, List<FaturaReferenteProduto> listaFaturaContem){
		//listaFaturaContem.g
		
		Fatura faturaTemp=faturaContemDao.buscarFaturaID(idFatura);
		
		if(faturaTemp==null){
			
			return 0;
		}
		
		
		else if(faturaContemDao.buscarFaturasComProdutos(faturaTemp)!=null){
			
			return 2;
		}
		return 1;
	}
	public List<FaturaReferenteProduto> detalhar(int idFatura){
		List<FaturaReferenteProduto> listaFaturaContem2;
		Fatura faturaTemp=faturaContemDao.buscarFaturaID(idFatura);
		
		listaFaturaContem2=faturaContemDao.buscarFaturasComProdutos(faturaTemp);
		return  listaFaturaContem2;
		
	}
	
	
	public int retornarPreco(int idFatura, Fatura aux2){
		
		
		aux2=faturaContemDao.buscarFaturaID(idFatura);
		if(aux2==null){
			return 0;
		}
		
		
		
		return 1;
		
		
	}
	public Fatura retoranarAUX(int idFatura){
		Fatura aux=new Fatura();
		aux=faturaContemDao.buscarFaturaID(idFatura);
		return aux;
	}

	
	
	
	
	
	
	
	
	
}