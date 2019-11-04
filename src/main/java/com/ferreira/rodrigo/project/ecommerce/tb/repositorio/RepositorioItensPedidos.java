package com.ferreira.rodrigo.project.ecommerce.tb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ferreira.rodrigo.project.ecommerce.tb.model.ItemPedido;

@Repository
public interface RepositorioItensPedidos extends JpaRepository<ItemPedido, Integer>{
	
}
