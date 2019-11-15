package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ferreira.rodrigo.project.ecommerce.tb.dto.CategoriaDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Categoria;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCategorias;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.*;


@Service
public class CategoriaService {
	
	@Autowired
	private RepositorioCategorias repo;
	
	public Categoria buscarCategoria(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName(), null));
	}
	

	public List<Categoria> buscarTodos() { // buscando todas as categorias.
		return repo.findAll();
	}
	
	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria atualizar(Categoria obj) {
		Categoria newObj = buscarCategoria(obj.getId());
		atualizarDado(newObj, obj);
		return repo.save(newObj);
		
	}
	private void atualizarDado(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

	
	public void deletar(Integer id) {
		buscarCategoria(id);
		try{
		repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria com produtos.");
		}
	}

	public Page<Categoria> buscaPorPagina(Integer page, Integer linhasPorPaginas, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linhasPorPaginas, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDto){ // Metodo auxiliar para converter categoria para categoriaDTO
		return new Categoria(objDto.getId(), objDto.getNome());
	}
}
