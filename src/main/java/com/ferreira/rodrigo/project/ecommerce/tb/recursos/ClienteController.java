package com.ferreira.rodrigo.project.ecommerce.tb.recursos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ferreira.rodrigo.project.ecommerce.tb.dto.ClienteDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.dto.NewClienteDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteController {
	@Autowired
	private ClienteService service; 
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {

		Cliente objBusca = service.buscarCliente(id);
		return ResponseEntity.ok().body(objBusca);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List <ClienteDTO>> buscarTodos() {
		System.out.println("ok controller");
		List <Cliente> listBusca = service.buscarTodos();
		List <ClienteDTO> listDTO = listBusca.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody NewClienteDTO objDto){ // inserindo cliente com o ClinteNewDTO
		Cliente obj = service.fromDTO(objDto);
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder
											.fromCurrentRequest()
											.path("/{id}")	       //metodo para recuperar uri e concatenar com uri inicial
											.buildAndExpand(obj.getId())
											.toUri(); 
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}" ,method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.atualizar(obj);
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@RequestBody Cliente obj, @PathVariable Integer id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscarPagina(
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "linhasPorPaginas", defaultValue = "24") Integer linhasPorPaginas, 
				@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page <Cliente> listBusca = service.buscaPorPagina(page, linhasPorPaginas, direction, orderBy);
		Page <ClienteDTO> listDTO = listBusca.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
		
	}
}
