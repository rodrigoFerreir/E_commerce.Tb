package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Categoria;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Cidade;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Endereco;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Estado;
import com.ferreira.rodrigo.project.ecommerce.tb.model.ItemPedido;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Pagamento;
import com.ferreira.rodrigo.project.ecommerce.tb.model.PagamentoComBoleto;
import com.ferreira.rodrigo.project.ecommerce.tb.model.PagamentoComCartao;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Pedido;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Produto;
import com.ferreira.rodrigo.project.ecommerce.tb.model.enuns.EstadoPagamento;
import com.ferreira.rodrigo.project.ecommerce.tb.model.enuns.TipoCliente;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCategorias;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCidade;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCliente;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioEndereco;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioEstado;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioItensPedidos;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioPagamento;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioPedidos;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioProdutos;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder Bcrypt;	
	@Autowired
	private RepositorioCategorias categoriaRepository;
	@Autowired
	private RepositorioProdutos produtoRepository;
	@Autowired
	private RepositorioEstado estadoRepository;
	@Autowired
	private RepositorioCidade cidadeRepository;
	@Autowired
	private RepositorioCliente clienteRepository;
	@Autowired
	private RepositorioEndereco enderecoRepository;
	@Autowired
	private RepositorioPedidos pedidoRepository;
	@Autowired
	private RepositorioPagamento pagamentoRepository;
	@Autowired
	private RepositorioItensPedidos itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategoria().addAll(Arrays.asList(cat1, cat4));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategoria().addAll(Arrays.asList(cat1, cat4));
		p4.getCategoria().addAll(Arrays.asList(cat2));
		p5.getCategoria().addAll(Arrays.asList(cat3));
		p6.getCategoria().addAll(Arrays.asList(cat3));
		p7.getCategoria().addAll(Arrays.asList(cat4));
		p8.getCategoria().addAll(Arrays.asList(cat5));
		p9.getCategoria().addAll(Arrays.asList(cat6));
		p10.getCategoria().addAll(Arrays.asList(cat6));
		p11.getCategoria().addAll(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "36378912377", "maria@gmail.com", TipoCliente.PESSOAFISICA, Bcrypt.encode("15467548"));
		Cliente cli2 = new Cliente(null, "Rodrigo da Silva", "36375476582", "RodrigoS@gmail.com", TipoCliente.PESSOAJURIDICA, Bcrypt.encode("367687557"));

		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "Altos do Mestre", "255", "Apto 8", "Zona Sul", "55572500", cli2, c3);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}// classe para teste de banco de dados H2 alimentando a base de dados.

}
