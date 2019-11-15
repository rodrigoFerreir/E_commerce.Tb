package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Categoria;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Produto;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCategorias;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioProdutos;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private RepositorioProdutos repositorioProduto;
	
	@Autowired
	private RepositorioCategorias repositorioCategorias;
	
	public List<Produto> buscarTodos() { // buscando todas os Produtos.
		return repositorioProduto.findAll();
	}
	
	public Produto buscarProduto(Integer id) {
		Optional<Produto> obj = repositorioProduto.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> buscar(String nome, List<Integer> ids, Integer page, Integer linhasPorPaginas, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linhasPorPaginas, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repositorioCategorias.findAllById(ids);
		return repositorioProduto.buscar(nome, categorias, pageRequest);
	}
}
