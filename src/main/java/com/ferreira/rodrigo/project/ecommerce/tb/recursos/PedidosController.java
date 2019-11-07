package com.ferreira.rodrigo.project.ecommerce.tb.recursos;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Pedido;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.PedidoService;
@RestController
@RequestMapping(value="/pedidos")
public class PedidosController {
	@Autowired
	private PedidoService service; 
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
	 Pedido objBusca = service.buscarPedido(id);
		return ResponseEntity.ok().body(objBusca);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido obj) {
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") // metodo para recuperar uri e concatenar com uri inicial
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
