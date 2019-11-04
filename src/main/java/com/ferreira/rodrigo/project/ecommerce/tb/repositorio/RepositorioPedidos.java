package com.ferreira.rodrigo.project.ecommerce.tb.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Cliente;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Pedido;

@Repository
public interface RepositorioPedidos extends JpaRepository<Pedido, Integer>{
	
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
