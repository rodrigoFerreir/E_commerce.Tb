package com.ferreira.rodrigo.project.ecommerce.tb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;

@Repository
public interface RepositorioCliente extends JpaRepository<Cliente, Integer>{
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
