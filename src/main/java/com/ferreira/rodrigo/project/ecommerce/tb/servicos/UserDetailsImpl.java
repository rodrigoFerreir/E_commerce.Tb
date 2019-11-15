package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;
import com.ferreira.rodrigo.project.ecommerce.tb.repositorio.RepositorioCliente;
import com.ferreira.rodrigo.project.ecommerce.tb.security.UserSecurity;

@Service
public class UserDetailsImpl implements UserDetailsService {  //implementando a busca do usuario do Spring Security pelo username (email nesse caso)
	
	@Autowired
	private RepositorioCliente repoCliente;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Cliente cli = repoCliente.findByEmail(email);
		
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
