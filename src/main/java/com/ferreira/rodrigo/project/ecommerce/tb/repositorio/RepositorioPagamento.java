package com.ferreira.rodrigo.project.ecommerce.tb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Pagamento;
@Repository
public interface RepositorioPagamento extends JpaRepository<Pagamento, Integer>{
	
}
