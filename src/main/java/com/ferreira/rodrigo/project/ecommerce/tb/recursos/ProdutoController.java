package com.ferreira.rodrigo.project.ecommerce.tb.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferreira.rodrigo.project.ecommerce.tb.dto.ProdutoDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Produto;
import com.ferreira.rodrigo.project.ecommerce.tb.recursos.utils.URL;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.ProdutoService;
@RestController
@RequestMapping(value="/produtos")
public class ProdutoController {
	@Autowired
	private ProdutoService service; 
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto objBusca = service.buscarProduto(id);
		return ResponseEntity.ok().body(objBusca);
		
	}
	
	@RequestMapping(value = "/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> buscarPagina(
				@RequestParam(value = "nome", defaultValue = "") String nome, 
				@RequestParam(value = "categorias", defaultValue = "") String categorias, 
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "linhasPorPaginas", defaultValue = "24") Integer linhasPorPaginas, 
				@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		String nomeDecoded = URL.decoderParam(nome);
		List<Integer> ids = URL.decoderIntList(categorias);
		Page <Produto> listBusca = service.buscar(nomeDecoded, ids, page, linhasPorPaginas, direction, orderBy);
		Page <ProdutoDTO> listDTO = listBusca.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
		
	}
}
