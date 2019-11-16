package com.ferreira.rodrigo.project.ecommerce.tb.recursos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ferreira.rodrigo.project.ecommerce.tb.dto.CategoriaDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Categoria;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> buscar(@PathVariable Integer id) {
		Categoria objBusca = service.buscarCategoria(id);
		return ResponseEntity.ok().body(objBusca);

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTodos() {
		List<Categoria> listBusca = service.buscarTodos();
		List<CategoriaDTO> listDTO = listBusca.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") // metodo para recuperar uri e concatenar com uri inicial
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.atualizar(obj);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@RequestBody Categoria obj, @PathVariable Integer id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> buscarPagina(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linhasPorPaginas", defaultValue = "24") Integer linhasPorPaginas,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Categoria> listBusca = service.buscaPorPagina(page, linhasPorPaginas, direction, orderBy);
		Page<CategoriaDTO> listDTO = listBusca.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);

	}

}
