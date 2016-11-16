package ejb;

import java.util.List;



import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.inject.Inject;

import dao.CategoriaDAO;

import dominio.Categoria;


@Stateless
public class categoriaService {
	@Inject
	private CategoriaDAO categoriaDAO;
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int cadastrarCategoria(Categoria categoria){
		
		Categoria c = categoriaDAO.buscarCategoriaNome(categoria.getNome());
		if (c == null) {
			categoriaDAO.salvar(categoria);
			
			
			return 1;
		} else {
			
			return 0;
		}

	}
	public List<Categoria> listarCategoria() {
		
		return categoriaDAO.listar();
	}
}