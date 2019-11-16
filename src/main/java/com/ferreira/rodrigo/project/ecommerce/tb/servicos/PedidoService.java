package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;
import com.ferreira.rodrigo.project.ecommerce.tb.model.ItemPedido;
import com.ferreira.rodrigo.project.ecommerce.tb.model.PagamentoComBoleto;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Pedido;
import com.ferreira.rodrigo.project.ecommerce.tb.model.enuns.EstadoPagamento;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioItensPedidos;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioPagamento;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioPedidos;
import com.ferreira.rodrigo.project.ecommerce.tb.security.UserSecurity;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.AuthorizationException;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private RepositorioPedidos repositorioPedido;
	
	@Autowired
	private RepositorioPagamento repositorioPagamento;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private RepositorioItensPedidos repositorioItensPedidos;
	
	@Autowired
	private ClienteService clienteService;

	
	public Pedido buscarPedido(Integer id) {  // sempre que for pesquisar por id
		Optional<Pedido> obj = repositorioPedido.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ". Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido inserir(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repositorioPedido.save(obj);
		repositorioPagamento.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.buscarProduto(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		repositorioItensPedidos.saveAll(obj.getItens());
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSecurity user = UserService.autheticaded();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); //buscando pedidos do cliente logado
		Cliente cliente =  clienteService.buscarCliente(user.getId());
		return repositorioPedido.findByCliente(cliente, pageRequest);
	}
	
}
