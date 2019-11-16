package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreira.rodrigo.project.ecommerce.tb.dto.ClienteDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.dto.NewClienteDTO;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Cidade;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Endereco;
import com.ferreira.rodrigo.project.ecommerce.tb.model.enuns.Perfil;
import com.ferreira.rodrigo.project.ecommerce.tb.model.enuns.TipoCliente;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCliente;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioEndereco;
import com.ferreira.rodrigo.project.ecommerce.tb.security.UserSecurity;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.AuthorizationException;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.DataIntegrityException;
import com.ferreira.rodrigo.project.ecommerce.tb.servicos.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder Bcrypt;

	@Autowired
	private RepositorioCliente repo;
	
	@Autowired
	private RepositorioEndereco enderecoRepository;

	public Cliente buscarCliente(Integer id) { // sempre que for pesquisar por id
		
		UserSecurity user = UserService.autheticaded();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ". Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> buscarTodos() { // buscando todas os clientes.
		return repo.findAll();
	}
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente atualizar(Cliente obj) {
		Cliente newObj = buscarCliente(obj.getId());
		atualizarDado(newObj, obj);
		return repo.save(newObj);
	}

	private void atualizarDado(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void deletar(Integer id) {
		buscarCliente(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir. Há entidades relacionadas.");
		}
	}

	public Page<Cliente> buscaPorPagina(Integer page, Integer linhasPorPaginas, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linhasPorPaginas, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) { // Metodo auxiliar para converter cliente para ClienteDTO
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(NewClienteDTO objDto) { // Metodo auxiliar para converter cliente para ClienteNewDTO
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getCpf(), objDto.getEmail(), TipoCliente.toEnum(objDto.getTipo()), Bcrypt.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeID(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), 
										objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		return cli;
	}
}
