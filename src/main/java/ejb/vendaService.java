package ejb;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import controllers.VendaMB.ProdutoModificado;
import dao.ClienteDAO;
import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import dao.VendaContemDAO;
import dao.VendaDAO;
import dominio.Cliente;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;
import dominio.VendaContem;

@Stateful
public class vendaService {
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}


	List<String> nomeProdutos;
	List<Double> quantidadeProdutos;
	List<Double> PrecoProdutos;
	double total;
	@Inject
	private VendaDAO vendaDao;	
	@Inject
	private VendaContemDAO vendaContemDao;
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private ProdutoDAO produtoDAO;
	
	private Venda nVenda;
	public Venda getnVenda() {
		return nVenda;
	}

	public void setnVenda(Venda nVenda) {
		this.nVenda = nVenda;
	}


	@Inject
	private ForneceProdutoDAO forneceContemDao;
	
	
	@Inject 
	FornecedorDAO fornecedorDAO;

	public vendaService() {
		super();
		nomeProdutos=new ArrayList<String>();
		quantidadeProdutos=new ArrayList<Double>();
		PrecoProdutos=new ArrayList<Double>();
		total=0.0;
	}
	public List<Venda> listar(){
		return vendaDao.listar();
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int adicionarAoCarrinho(String nomeProdCarrinho,double quntProdCarrinho, ArrayList<ProdutoModificado> tabelaVenda) {
		
		if(nomeProdCarrinho.equals("")){
			return 0;
		}
		if(quntProdCarrinho<=0){
			
			
			return 1;
		}
		
		double ValorTruncado=truncarQuantidade(quntProdCarrinho);
		quntProdCarrinho=ValorTruncado;
		
		Produto produtoTemp=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		 
		
		
		
		if(produtoTemp==null){
			
			
			return 2;
			
		}
		
		String tempNome=produtoTemp.getNome();
		int swit=-1;
		
		if(quntProdCarrinho % 1 != 0 && produtoTemp.isFracionavel()==false){
			
			return 3;
		}
		
		
		
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
		}
		
		if(swit!=-1){
			Double auxiliar=(Double) quantidadeProdutos.get(swit);
			quntProdCarrinho=quntProdCarrinho+auxiliar;
		}
		
		
		
				
		if(produtoTemp.getQuantidade()<quntProdCarrinho){
			
			return 4;
		}
		
		
		
		if(swit==-1){
		nomeProdutos.add(nomeProdCarrinho);
		quantidadeProdutos.add(quntProdCarrinho);
		PrecoProdutos.add(produtoTemp.getPreco());
		}else{
			quantidadeProdutos.set(swit, quntProdCarrinho);
		}
		
		
		tabelaVenda.clear();
		total=0.0;
		
		
		for(int j=0;j<nomeProdutos.size();j++){
			
			double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
			
			double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
			double arredondarTotal=truncarValor(ValorTotalAnterior);
			total=total+arredondarTotal;
			int resultado2=addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondarTotal,tabelaVenda,nomeProdCarrinho);
						
			
			
			
		}
		total=truncarValor(total);
		
		return 6;

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int remover(String nomeProdCarrinho,double quntProdCarrinho, ArrayList<ProdutoModificado> tabelaVenda) {
		
	
		if(nomeProdCarrinho.equals("")){
			
			return 0;
		}
		
		
		
		quntProdCarrinho=truncarQuantidade(quntProdCarrinho);
		
		
		if(quntProdCarrinho<=0){
			
			
			return 1;
		}
		
		Produto produto=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			
			return 2;
			
		}
		
		
		String tempNome=produto.getNome();
		
		
		if(!nomeProdutos.contains(tempNome)){
		
			
			return 3;				
		}
		
		if(quntProdCarrinho % 1 != 0 && produto.isFracionavel()==false){
			
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
			
			
						
			tabelaVenda.clear();
			total=0.0;
			for(int j=0;j<nomeProdutos.size();j++){
				
				double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
				double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
				double arredondar=truncarValor(ValorTotalAnterior);
				
				total=total+arredondar;
				int resultado2=addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondar,tabelaVenda,nomeProdCarrinho);
				
				
				
			}
			
			total=truncarValor(total);
		}
			
			
			return 6;
			
		

	}
	
	public double buscarQuantidadeEstoque(String nome){
		
		Produto ProdutoTemp=produtoDAO.buscarProdutoNome(nome);
		
		return ProdutoTemp.getQuantidade();
		
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int finalizarAsCompras(Funcionario Vendedor,Long idClientela ,ArrayList<ProdutoModificado> tabelaVenda, String tipoDesconto) {
		
		Cliente dud=new Cliente();
		Venda nVenda=new Venda();
		
		if(total==0.0){
			
			return 0;
		}
		Produto produtoTemp=new Produto();
		//System.out.println(idClientela);
		Cliente clienteTemp=new Cliente();
		double auxiliararitmetico;
		if(clienteDAO.buscarClienteCPF(idClientela)==null){
			
			if(idClientela==1){
				long temp=1;
				dud=new Cliente(temp,"26-09-1994","Nao especificado");
				clienteDAO.salvar(dud);
				nVenda.setIDcliente(dud);
			}else{
				FacesMessage msg = new FacesMessage("Cliente não existe");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				return 1;
			}
		}
		
		
		clienteTemp=clienteDAO.buscarClienteCPF(idClientela);
		
		
		
		
		
		nVenda.setIDvendedor(Vendedor);
		nVenda.setValorTotal(total);
		nVenda.setIDcliente(clienteTemp);
		nVenda.setTipoDesconto(tipoDesconto);
		System.out.println("macarena");
		System.out.println(clienteTemp.getCPF());


		
		
		vendaDao.salvar(nVenda);
		
		for(int i=0;i<nomeProdutos.size();i++){
			VendaContem vendaContemTEMP=new VendaContem();
			Produto temp2=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			vendaContemTEMP.setVenda_id(nVenda);
			vendaContemTEMP.setQuantidadeVenda(quantidadeProdutos.get(i));
			vendaContemTEMP.setNomeProduto(temp2);
			vendaContemTEMP.setPrecoNoMomento(PrecoProdutos.get(i));
			produtoTemp=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			auxiliararitmetico=produtoTemp.getQuantidade()-quantidadeProdutos.get(i);
			produtoTemp.setQuantidade(auxiliararitmetico);
			produtoDAO.atualizar(produtoTemp);

			vendaContemDao.salvar(vendaContemTEMP);
		}
		
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		total=0.0;
		return 2;
		
		
		//return "/interna/sucesso.jsf";
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

		public void limpar() {
			// TODO Auto-generated method stub
			 nomeProdutos.clear();
			 quantidadeProdutos.clear();
			 PrecoProdutos.clear();		
			 total=0.0;
		}
		public List<Venda> listarVenda() {
			
			return vendaDao.listar();
		}
		
		public int filtrarVenda(int idVenda){
			//listaFaturaContem.g
			Venda VendaTemp=vendaContemDao.buscarVendaID(idVenda);
			//Fornecedor fornecedorTemporario=forneceContemDao.buscarFornecedorpeloNome(idFornecimento);
			if(VendaTemp==null){
				
				return 0;
			}
			return 1;
		}
		
		public List<VendaContem> detalhar(int idVenda){
			List<VendaContem> listaFaturaContem2;
			Venda VendaTemp=vendaContemDao.buscarVendaID(idVenda);
			listaFaturaContem2=vendaContemDao.buscarVendasComProdutos(VendaTemp);
			//Fornecedor temp=fornecedorDAO.buscarFornecedorNome(idFornecedor);
			//listaFaturaContem2=forneceContemDao.buscarFornecimentoComFornecedor(temp);
			
			return  listaFaturaContem2;
			
		}
	
}